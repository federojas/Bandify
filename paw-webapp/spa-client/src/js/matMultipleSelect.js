document.addEventListener("DOMContentLoaded", ready);
function ready() {
    window.scrollTo(0, 0);
}

$(document).ready(function () {
    $('select').material_select();

    $(".select-wrapper").each(function () {
        var wrapper = this;
        $(this).find("ul>li").each(function () {
            var li = this;
            var option_text = $(this).text();


            $(wrapper).find("select option:selected").each(function () {
                var selected_text = $(this).text();

                if (option_text == selected_text) {
                    // $(li).addClass("active selected");
                    // $(li).find("input").prop('checked', true);
                    $(li).click();
                }


            });

            $(li).on("click", function () {
                text_value = $(li).text();
                selected = $(li).hasClass("active");
                $(wrapper).find("select option").each(function () {
                    if ($(this).text() == text_value) {
                        $(this).attr("selected", selected);
                    }
                })
            });

        });


    });

    $("#title").click();
});