const fs = require("fs-extra");

const templates = {
  default: {
    js: fs.readFileSync("template.js", "utf-8"),
    html: fs.readFileSync("template.html", "utf-8"),
    dialog: fs.readFileSync("dialog/template.xml", "utf-8"),
    contentxml: fs.readFileSync("template.xml", "utf-8"),
  },
};

module.exports.templates = templates;

console.log("TEMPLATE: ", templates.default.js);
