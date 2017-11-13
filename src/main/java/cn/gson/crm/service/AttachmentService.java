package cn.gson.crm.service;

import cn.gson.crm.common.AttachmentType;
import cn.gson.crm.model.dao.AttachmentDao;
import cn.gson.crm.model.domain.Attachment;
import cn.gson.crm.model.domain.Member;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.crm.service</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月12日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Service
@Transactional(readOnly = true)
public class AttachmentService {

    Logger logger = Logger.getLogger(AttachmentService.class);

    @Autowired
    AttachmentDao attachmentDao;

    /**
     * 可以在配置文件，配置附件存储目录
     */
    @Value("${crm.attachment.root-path}")
    private String rootPath;

    private String separator = File.separator;

    @Value("${crm.attachment.allow-file-type}")
    private String allowFileType;

    /**
     * 存储附件
     *
     * @param member
     * @param type
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @Transactional
    public Attachment saveFile(Member member, AttachmentType type, MultipartFile multipartFile) throws Exception {
        //判断文件是否允许呗
        if (this.getAllowFileType().contains("." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()))) {

            Attachment att = new Attachment();
            att.setMember(member);
            att.setOriginalName(multipartFile.getOriginalFilename());
            att.setFileSize(multipartFile.getSize());
            att.setSuffix(FilenameUtils.getExtension(att.getOriginalName()));
            att.setContentType(multipartFile.getContentType());
            att.setType(type);

            //新文件名
            String fileName = UUID.randomUUID().toString().toLowerCase() + "." + att.getSuffix();
            //文件的存储的相对路径
            String filePath = this.getSaveRootPath(type) + fileName;

            try {
                //将文件存储到实际存储目录中
                multipartFile.transferTo(new File(this.getRootPath(), filePath));

                att.setFilePath("/attachment/" + filePath);

                attachmentDao.save(att);
                return att;
            } catch (Exception e) {
                logger.error("上传文件存储失败！", e);
                throw new Exception("上传文件存储失败", e);
            }
        } else {
            throw new Exception("对不起，不支持上传此类型文件！");
        }
    }

    /**
     * 获取附件文件
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public Attachment getFile(String filePath) throws FileNotFoundException {
        Attachment att = attachmentDao.findByFilePath(filePath);
        File file = new File(this.getRootPath(), filePath.substring("/attachment".length()));
        if (att == null || !file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        att.setFile(file);
        return att;
    }

    /**
     * 清除用户无用头像
     *
     * @param member
     */
    public void clearAvatar(Member member) {
        //获取到非当前头像的历史头像记录
        List<Attachment> avatars = attachmentDao.findByFilePathIsNotAndMemberAndType(member.getAvatar(), member, AttachmentType.AVATAR);
        //物理删除
        avatars.forEach((Attachment item) -> {
            try {
                FileUtils.forceDelete(new File(getRootPath(), item.getFilePath().substring("/attachment".length())));
            } catch (IOException e) {
                logger.error("头像删除失败：" + item, e);
            }
        });
        //数据库删除
        attachmentDao.delete(avatars);
    }

    /**
     * 获取文件存储的绝对路径
     *
     * @param type
     * @return
     */
    private String getSaveRootPath(AttachmentType type) {
        //附件按照类型和年月分目录存储，至于为什么，是考虑目录文件检索效率问题
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + separator + "MM");
        //实际文件存储路径
        String path = type.name().toLowerCase() + separator + sdf.format(new Date()) + separator;

        //判断存储路径是否已经存在，不存在则需要先创建出来
        File f = new File(getRootPath(), path);
        if (!f.exists()) {
            try {
                FileUtils.forceMkdir(f);
            } catch (IOException e) {
                logger.error("创建存储目录失败！", e);
            }
        }

        return path;
    }

    /**
     * 获取根路径
     *
     * @return
     */
    private String getRootPath() {
        if (StringUtils.isEmpty(rootPath)) {
            //如果没有设置附件存储根目录，这默认存储到系统用户目录中
            rootPath = FilenameUtils.concat(FileUtils.getUserDirectoryPath(), "crm_attachments");
        }
        return rootPath;
    }

    /**
     * 允许上传的文件类型
     *
     * @return
     */
    private List<String> getAllowFileType() {
        if (StringUtils.isEmpty(allowFileType)) {
            allowFileType = ".jpg,.png,.gif,.bmp,.jpeg,.zip,.rar";
        }
        return Arrays.asList(StringUtils.split(allowFileType, ","));
    }
}
