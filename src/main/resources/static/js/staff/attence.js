define(function () {
    return function () {
        $('#cc').calendar({
            current: new Date(),
            formatter: function (date) {
                return "<br/>" + date.getDate() + '<div style="position:absolute; top:5px; right:3px; width:60px;">Tip info.</div>';
            }
        });
    }
}