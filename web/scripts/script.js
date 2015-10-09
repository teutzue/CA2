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
                    trHTML += '<tr><td>' + item.firstName + '</td><td>' + item.lastName + '</td><td>'
                            + item.email + '</td><td><a href="#" class="delete">delete</a> <a class="edit">edit</a></td></tr>';
                });
                $('.table-striped').append(trHTML);
            }
        });
    });
});
