$(document).ready(function () {

    var g_amount_td = $("#g_amount");
    var w_amount_td = $("#w_amount");
    var b_amount_td = $("#b_amount");

    var g_expenses_month_td = $("#g_expenses_month");
    var w_expenses_month_td = $("#w_expenses_month");
    var b_expenses_month_td = $("#b_expenses_month");

    var g_expenses_year_td = $("#g_expenses_year");
    var w_expenses_year_td = $("#w_expenses_year");
    var b_expenses_year_td = $("#b_expenses_year");

    //price for gas per m^3
    var g_price;

    //briquettes price per kg
    var b_price;

    //wood price per kg;
    var w_price;

    function getPrices() {
        $.ajax({
            url: "./price",
            type: "GET",
            contentType: "application/JSON",
            dataType: "json",
            success: function (responce) {
                assign_prices(responce);
            },
            error: function () {
                alert("Server not responding =(")
            }
        })
    }

    function getPrice(priceName, prices) {
        return prices.filter(function (price) {
            return price.priceName == priceName;
        })[0]['price']
    }

    function assign_prices(responce) {
        g_price = getPrice("natural_gas", responce);
        b_price = getPrice("briquettes", responce);
        w_price = getPrice("wood", responce);
    }

    getPrices();
    $("#calculator-input").keyup(function () {


            //gas amount per moth
            var g_amount = $(this).val();
            //gas combustion heat coefficient per m ^ 3 in kkal
            var g = 7600;
            //gas expenses per month
            var g_expenses = g_amount * g_price;

            //amount of energy consumed by user per month in kkal
            var total_energy = g_amount * g;

            //wood combustion heat coefficient per kg in kkal
            var w = 2200;
            //necessary wood amount kg
            var w_amount = total_energy / w;
            //expanses on wood per month
            var w_expenses = w_amount * w_price;

            //briquettes combastion heat coefficient per kg in kkal
            var b = 4800;
            //necessary briquettes amount kg
            var b_amount = total_energy / b;
            //expenses per month for briquettes
            var b_expenses = b_amount * b_price;

            g_expenses_month_td.text(Math.round(g_expenses).toLocaleString() + " UAH");
            g_expenses_year_td.text(Math.round(g_expenses * 12).toLocaleString() + " UAH");
            g_amount_td.html(Math.round(g_amount).toLocaleString() + " m<sup>3</sup>");

            w_expenses_month_td.text(Math.round(w_expenses).toLocaleString() + " UAH");
            w_expenses_year_td.text(Math.round(w_expenses * 12).toLocaleString() + " UAH");
            w_amount_td.text(Math.round(w_amount).toLocaleString() + " kg");

            b_expenses_month_td.text(Math.round(b_expenses).toLocaleString() + " UAH");
            b_expenses_year_td.text(Math.round(b_expenses * 12).toLocaleString() + " UAH");
            b_amount_td.text(Math.round(b_amount).toLocaleString() + " kg");
        }
    )
});