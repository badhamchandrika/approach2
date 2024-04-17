(function($) {
      $(window).adaptTo("foundation-registry").register("foundation.validation.validator", {
            selector: "coral-multifield",
            validate: function(el) {
                  let totalPanels = el.items.getAll().length;
                  let max = window.location.pathname.split("/").length - (parseInt($("form.foundation-form").find("input[name='./startLevel']").val()) + 3);
                  if(totalPanels > max) {
                        return "Maximum numbers of items allowed are: " + max;
                  }
            }
      });   
})($);