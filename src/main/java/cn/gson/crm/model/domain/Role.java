package cn.gson.crm.model.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "crm_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(targetEntity=Resource.class)
	@JoinTable(name="crm_role_resource",
	joinColumns={
			@JoinColumn(name="role_id")
	},inverseJoinColumns={
			@JoinColumn(name="resource_id")
	})
    private List<Resource> resource;
	
	@Column(length = 30, unique = true, nullable = false)
	private String roleName;

	@Column(length = 512)
	private String description;
	
	private Boolean status = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public List<Resource> getResource() {
		return resource;
	}
	
	public void setResource(List<Resource> resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", description=" + description + ", status=" + status
				+ "]";
	}
}
