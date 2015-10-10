/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    // Handler for .ready() called.
    //Display persons in table
    $(function () {
        $.ajax({
            type: 'GET',
            url: "api/person/complete",
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            success: function (data) {
                var trHTML = '';
                $.each(data, function (i, item) {
                    trHTML += '<tr>\n\\n\
                    <td id="item.id">' + item.id + '</td>\n\
                    <td>' + item.firstName + '</td>\n\
                    <td>' + item.lastName + '</td>\n\
                    <td>' + item.email + '</td>\n\
                    <td><a href="#" class="delete">Delete</a> <a class="edit">Edit</a></td>\n\
                    </tr>';
                });
                $('.table-striped').append(trHTML);
            }
        });

    });
//    $('.delete').click(function () {
//        var id = $(this ~ tr).attr("id");
//        alert(id);
//        $.ajax({
//            type: "DELETE",
//            url: "api/person/complete" + id,
//            success: function (msg) {
//                alert(id);
//                //$(id).remove();
//                //location.reload();
//            }
//        });
//    });
});

