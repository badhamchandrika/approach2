const { readFileSync } = require("fs");
const path = require("path");

const templatedir = `${
  __dirname.split("ui.frontend")[0]
}ui.frontend/src/main/template/`;

const templates = {
  default: {
    js: readFileSync(`${templatedir}template.js`, "utf-8"),
    test: readFileSync(`${templatedir}template.test.js`, "utf-8"),
    html: readFileSync(`${templatedir}template.html`, "utf-8"),
    dialog: readFileSync(`${templatedir}dialog/template.xml`, "utf-8"),
    contentxml: readFileSync(`${templatedir}template.xml`, "utf-8"),
    sass: readFileSync(`${templatedir}template.scss`, "utf-8"),
  },
};

exports.templates = templates;
