const gulp = require("gulp");
const handlebars = require("gulp-compile-handlebars");
const rename = require("gulp-rename");
const sass = require("gulp-sass")(require("sass"));
const argv = require("yargs").argv;
const fs = require("fs-extra");
const { readFiles } = require("./src/main/utils/readFiles");
const { writeFiles } = require("./src/main/utils/writeFiles");
const livedemo = require("./livedemo.js");
const { templates } = require("./createComponent/templates");

/*
 *
 *   Handlebars Compile Task:
 *   Usage: gulp hbs --component <COMPONENT_NAME> --theme <THEME>
 *
 */
async function hbs() {
  const componentName = argv?.component || "test";
  const themeName = argv?.theme || "corporate";
  let templateData;
  let options;

  // Get template data
  fs.readJSON(
    `./src/main/webpack/${themeName}/components/${componentName}/${componentName}.json`
  ).then((data) => {
    templateData = data;
  });

  options = {
    ignorePartials: true,
    batch: [`./src/main/webpack/${themeName}/partials`],
    helpers: {
      capitals: function (str) {
        return str.toUpperCase();
      },
    },
  };

  await setTimeout(() => {
    return gulp
      .src(`src/**/**/**/${componentName}.hbs`)
      .pipe(handlebars(templateData, options))
      .pipe(rename(`${componentName}.html`))
      .pipe(gulp.dest(`${themeName}/components/${componentName}`));
  }, 1000);
}

exports.hbs = hbs;

/*
 *
 *   SASS Compile Task
 *   Usage: gulp buildSass --component <COMPONENT_NAME> --theme <THEME>
 *
 */
function buildSass() {
  const themeName = argv?.theme || "corporate";
  const componentName = argv?.component || "test";

  return gulp
    .src(`src/**/**/${themeName}/site/*.scss`)
    .pipe(sass().on("error", sass.logError))
    .pipe(rename(`main.css`))
    .pipe(gulp.dest(`${themeName}/components/${componentName}`));
}

exports.buildSass = buildSass;

/*
 *
 *   JS Task
 *   Usage: gulp compileJS --component <COMPONENT_NAME> --theme <THEME>
 *
 */
function compileJS() {
  const themeName = argv?.theme || "corporate";
  const componentName = argv.component || "test";

  return gulp
    .src(`src/**/**/**/${componentName}.js`)
    .pipe(rename(`main.js`))
    .pipe(gulp.dest(`${themeName}/components/${componentName}`));
}

exports.compileJS = compileJS;

/*
 *
 *   Generate Component
 *   Usage: gulp generateComponent --component <COMPONENT_NAME> --theme <THEME> --jcrtitle<TITLE_OF_COMPONENT>
 *
 */
function generateComponent(done) {
  const componentName = argv?.component;
  const themeName = argv?.theme;
  const componentTitle = argv?.jcrtitle;
  const template = argv?.template;
  const outPutComponent = `src/main/webpack/${themeName}/components/${componentName}/${componentName}`;
  const AEMComponent = `../ui.apps/src/main/content/jcr_root/apps/aem-airmiles-web/components/${componentName}`;
  const templateDir = "src/main/template/template";
  const templateHTMLFile = readFiles(templateDir, "html");
  const templateSassFile = readFiles(templateDir, "scss").replace(
      "<COMP_TITLE>",
      componentTitle || componentName
  );
  const templateTestFile = readFiles(templateDir, "test.js");
  const templateJsFile = readFiles(templateDir, "js");
  const templateXMLFile = readFiles(templateDir, "xml").replace(
    "<COMP_TITLE>",
    componentTitle || componentName
  );

  const cqDialogTemplate = fs.readFileSync(
    `src/main/template/dialog/template.xml`
  );

  writeFiles(`${AEMComponent}/${componentName}.html`, templates[template].html);
  writeFiles(
    `${AEMComponent}/.content.xml`,
    templates[template].contentxml.replace(
      "<COMP_TITLE>",
      componentTitle || componentName
    )
  );
  writeFiles(
    `${AEMComponent}/_cq_dialog/.content.xml`,
    templates[template].dialog
  );
  writeFiles(`${outPutComponent}.scss`, templates[template].sass.replace(
          "<COMP_TITLE>",
          componentTitle || componentName
      )
  );
  writeFiles(`${outPutComponent}.test.js`, templates[template].test);
  writeFiles(`${outPutComponent}.js`, templates[template].js);

  // Import files in main.ts and main.scss
  const entryPointPath = "./src/main/webpack/corporate/site/main";

  const jsEntryPoint =
    fs.readFileSync(`${entryPointPath}.ts`, "utf-8") +
    `import "../${outPutComponent}"; \n`;

  const scssEntryPoint =
    fs.readFileSync(`${entryPointPath}.scss`, "utf-8") +
    `@import "../${outPutComponent}"; \n`;

  fs.writeFileSync(`${entryPointPath}.ts`, jsEntryPoint);
  fs.writeFileSync(`${entryPointPath}.scss`, scssEntryPoint);

  done();
}

exports.generateComponent = generateComponent;

/*
 *
 *   demo
 *   Usage: gulp demo --file <FILE_TO_WATCH>

 *
 *   Filepath should be relative to ui.frontend
 */
function demo() {
  const fileToWatch = argv.file;
  gulp.watch([fileToWatch], function (cb) {
    livedemo(fileToWatch);
    cb();
  });
}

exports.demo = demo;

/*
 *
 *   Default gulp task
 *   Usage: gulp --component <COMPONENT_NAME> -- <THEME>
 *
 */
exports.default = gulp.series(hbs, buildSass, compileJS);
