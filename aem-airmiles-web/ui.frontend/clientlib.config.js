/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2020 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

const path = require('path');

const BUILD_DIR = path.join(__dirname, 'dist');
const CLIENTLIB_DIR = path.join(
  __dirname,
  '..',
  'ui.apps',
  'src',
  'main',
  'content',
  'jcr_root',
  'apps',
  'aem-airmiles-web',
  'clientlibs'
);

const libsBaseConfig = {
  allowProxy: true,
  serializationFormat: 'xml',
  cssProcessor: ['default:none', 'min:none'],
  jsProcessor: ['default:none', 'min:none']
};

// Config for `aem-clientlib-generator`
module.exports = {
  context: BUILD_DIR,
  clientLibRoot: CLIENTLIB_DIR,
  libs: [
    //Common Clientlibs
    {
      ...libsBaseConfig,
      name: 'clientlib-dependencies',
      categories: ['aem-airmiles-web-dependencies'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-dependencies',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-dependencies-site',
          files: ['**/*.css'],
          flatten: false
        }
      }
    },
    {
      ...libsBaseConfig,
      name: 'clientlib-dependencies',
      categories: ['aem-airmiles-web.base'],
      dependencies: ['aem-airmiles-web.grid', 'core.wcm.components.accordion.v1'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-dependencies',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-dependencies-site',
          files: ['**/*.css'],
          flatten: false
        },
        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: 'clientlib-dependencies',
          files: ['**/*.*'],
          flatten: false,
          ignore: ['**/*.js', '**/*.css']
        }
      }
    },
    //Corporate Clientlibs
    {
      ...libsBaseConfig,
      name: 'clientlib-corporate-dependencies',
      categories: ['aem-airmiles-web-corporate.dependencies'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-corporate_dependencies-site',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-corporate_dependencies-site',
          files: ['**/*.css'],
          flatten: false
        }
      }
    },
    {
      ...libsBaseConfig,
      name: 'clientlib-corporate-site',
      categories: ['aem-airmiles-web-corporate.site'],
      dependencies: ['granite.utils','aem-airmiles-web-corporate.dependencies'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-corporate-site',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-corporate-site',
          files: ['**/*.css'],
          flatten: false
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: 'clientlib-corporate-site',
          files: ['**/*.*'],
          flatten: false,
          ignore: ['**/*.js', '**/*.css']
        }
      }
    },
    //Offers Clientlibs
    {
      ...libsBaseConfig,
      name: 'clientlib-offers-dependencies',
      categories: ['aem-airmiles-web-offers.dependencies'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-offers_dependencies-site',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-offers_dependencies-site',
          files: ['**/*.css'],
          flatten: false
        }
      }
    },
    {
      ...libsBaseConfig,
      name: 'clientlib-offers-site',
      categories: ['aem-airmiles-web-offers.site'],
      dependencies: ['aem-airmiles-web-offers.dependencies'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-offers-site',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-offers-site',
          files: ['**/*.css'],
          flatten: false
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: 'clientlib-offers-site',
          files: ['**/*.*'],
          flatten: false,
          ignore: ['**/*.js', '**/*.css']
        }
      }
    },
    {
      ...libsBaseConfig,
      name: 'clientlib-shell-site',
      categories: ['aem-airmiles-web-shell.site'],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: 'clientlib-shell-site',
          files: ['**/*.js'],
          flatten: false
        },
        css: {
          cwd: 'clientlib-shell-site',
          files: ['**/*.css'],
          flatten: false
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: 'clientlib-shell-site',
          files: ['**/*.*'],
          flatten: false,
          ignore: ['**/*.js', '**/*.css']
        }
      }
    }
  ]
};
