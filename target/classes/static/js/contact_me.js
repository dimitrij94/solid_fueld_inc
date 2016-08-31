// Contact Form Scripts

$.ajaxSetup({
    beforeSend: function (xhr, settings) {
        if (settings.type == 'POST' || settings.type == 'PUT' || settings.type == 'DELETE') {
            function getCookie(name) {
                var cookieValue = null;
                if (document.cookie && document.cookie != '') {
                    var cookies = document.cookie.split(';');
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = jQuery.trim(cookies[i]);
                        // Does this cookie string begin with the name we want?
                        if (cookie.substring(0, name.length + 1) == (name + '=')) {
                            cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                            break;
                        }
                    }
                }
                return cookieValue;
            }

            if (!(/^http:.*/.test(settings.url) || /^https:.*/.test(settings.url))) {
                // Only send the token to relative URLs i.e. locally.
                xhr.setRequestHeader("X-XSRF-TOKEN", getCookie('XSRF-TOKEN'));
            }
        }
    }
});
var address;

$(function () {
    ymaps.ready(init);
    var myMap;
    function convertAddress(responce) {
        var locality = responce.properties._data.geocoderMetaData.AddressDetails.Country.AdministrativeArea.SubAdministrativeArea.Locality;
        address =
        {
            latitude: responce.geometry._coordinates[1],
            longitude: responce.geometry._coordinates[0],
            city: locality.LocalityName,
            street: locality.Thoroughfare.ThoroughfareName,
            building: locality.Thoroughfare.Premise.PremiseNumber
        };
        return address;
    }

    function init() {
        myMap = new ymaps.Map("map", {
            center: [50.736616, 24.162471],
            zoom: 12,
            controls:['geolocationControl','fullscreenControl']
        });
        var searchControl = new ymaps.control.SearchControl({
            options: {
                // Будет производиться поиск и по топонимам и по организациям.
                provider: 'yandex#search',
                kind:'house',
                boundedBy:[[50.946536, 23.969486],[50.45363,24.817244]]
            }
        });
        searchControl.events.add('resultselect', function (e) {
            var index = searchControl.getSelectedIndex(e);
            console.log("Индекс выбранного элемента: " + index);
            var result = searchControl.getResult(index);
            result.then(function (res) {
                console.log("Результат " + res);
                address = res;
            }, function (err) {
                console.log("Ошибка");
            });
        });
        myMap.controls.add(searchControl);

    }

    $("#contactForm input,#contactForm textarea").jqBootstrapValidation({
        preventSubmit: true,
        submitError: function ($form, event, errors) {
            // additional error messages or events
        },
        submitSuccess: function ($form, event) {
            event.preventDefault(); // prevent default submit behaviour
            if(address==undefined) throw "Аддрес не заполнен";
            // get values from FORM
            var name = $("input#name").val();
            var email = $("input#email").val();
            var phone = $("input#phone").val();
            var message = $("textarea#message").val();
            var amount = $("input#amount_ordered").val();
            var firstName = name; // For Success/Failure Message
            // Check for white space in name for Success/Fail message
            if (firstName.indexOf(' ') >= 0) {
                firstName = name.split(' ').slice(0, -1).join(' ');
            }
            $.ajax({
                url: "./order",
                type: "POST",
                contentType: "application/JSON",
                dataType: "json",
                data: JSON.stringify({
                    "client": {
                        "name": name,
                        "phone": phone,
                        "email": email
                    },
                    "orderAddress": convertAddress(address),
                    "message": message,
                    "quantityKG": amount
                }),
                cache: false,
                success: function () {
                    // Success message
                    $('#success').html("<div class='alert alert-success'>");
                    $('#success > .alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                        .append("</button>");
                    $('#success > .alert-success')
                        .append("<strong>Your message has been sent. </strong>");
                    $('#success > .alert-success')
                        .append('</div>');

                    //clear all fields
                    $('#contactForm').trigger("reset");
                },
                error: function () {
                    // Fail message
                    $('#success').html("<div class='alert alert-danger'>");
                    $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                        .append("</button>");
                    $('#success > .alert-danger').append("<strong>Вибачте " + firstName + ", сервер не відповідає, можливо ви не заповнили поле адреси!");
                    $('#success > .alert-danger').append('</div>');
                    //clear all fields
                    $('#contactForm').trigger("reset");
                },
            });
        },
        filter: function () {
            return $(this).is(":visible");
        },
    });

    $("a[data-toggle=\"tab\"]").click(function (e) {
        e.preventDefault();
        $(this).tab("show");
    });
});


/*When clicking on Full hide fail/success boxes */
$('#name').focus(function () {
    $('#success').html('');
});
