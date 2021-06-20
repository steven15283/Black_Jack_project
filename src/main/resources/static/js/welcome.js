function joinGame() {
    var values = {};
    $.each($('#newPlayer').serializeArray(), function(i, field) {
        values[field.name] = field.value;
    });

    $.ajax({
        type: 'POST',
        url: "/api/v1/register/newPlayer/",
        data: JSON.stringify(values),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            if(data) {
                window.location.href = "/" + values.room + "/user/" + values.username;
            } else {
                alert("you can't join that room, max of 5 already full")
            }
        }
    });

}