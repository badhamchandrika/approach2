$(window).adaptTo("foundation-registry").register("foundation.validation.validator", {
    selector: "[data-foundation-validation^='multifield-min-max']",
    validate: function(multifieldNode) {
      //if validation for min value is optional, only return
      if (!multifieldNode.getAttribute('aria-required') && multifieldNode.items.length == 0) return;
  
      var validationName = multifieldNode.getAttribute("data-validation");
      var minMax = validationName.replace("multifield-min-max-", "").split(",");
      var minItems = parseInt(minMax[0])
      var maxItems = parseInt(minMax[1]);
  
      if (maxItems == 0 && multifieldNode.items.length < minItems){
          return "Minimum number of items required: " + minItems + " items.";
      } else if (minItems == 0 && multifieldNode.items.length > maxItems){
          return "Maximum number of items allowed: " + maxItems + " items.";
      } else if ( (multifieldNode.items.length < minItems || multifieldNode.items.length > maxItems) && maxItems != 0){
          return "Minimum number of items required: " + minItems + " and maximum: " + maxItems + " items.";
      }
    }
  });