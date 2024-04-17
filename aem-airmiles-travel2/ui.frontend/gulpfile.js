const argv = require("yargs").argv;
const { writeFiles } = require("./src/utils/writeFiles");
const { templates } = require("./createComponent/templates"); 

/*
 *
 *   Generate Component
 *   Usage: gulp generateComponent --component <COMPONENT_NAME> --theme <THEME> --jcrtitle<TITLE_OF_COMPONENT>
 *
 */

function generateComponent(done) {
  const componentName = argv?.component;
  const componentTitle = argv?.jcrtitle;
  const template = argv?.template;
  const outPutComponent = `src/main/webpack/components/${componentName}/${componentName}`;
  const AEMComponent = `../ui.apps/src/main/content/jcr_root/apps/aem-airmiles-travel2/components/${componentName}`;

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
  writeFiles(`${outPutComponent}.scss`, templates[template].sass);
  writeFiles(`${outPutComponent}.test.js`, templates[template].test);
  writeFiles(`${outPutComponent}.js`, templates[template].js);

  done();
}

exports.generateComponent = generateComponent;
