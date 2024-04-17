const staticIndexMarkup = (compMarkup) => `<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Static Frontend file</title>
    <meta name="template" content="page-content" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link
      rel="stylesheet"
      href="/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-base.css"
      type="text/css"
    />
    <script
      type="text/javascript"
      src="/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-dependencies.js"
    ></script>
    <link
      rel="stylesheet"
      href="/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-dependencies.css"
      type="text/css"
    />
  </head>
  <body class="page basicpage">
    <div class="root container responsivegrid">
      <div class="cmp-container">
        <header class="experiencefragment">
          <div class="cmp-experiencefragment">
            <div class="cmp-container">
              <div class="navigation">
                <nav
                  class="cmp-navigation"
                  role="navigation"
                  itemscope
                  itemtype="http://schema.org/SiteNavigationElement"
                >
                  <ul class="cmp-navigation__group">
                    <li
                      class="cmp-navigation__item cmp-navigation__item--level-0 cmp-navigation__item--active"
                    >
                      <a
                        href="/content/aem-airmiles-web/us/en.html"
                        title="Airmiles-Web"
                        aria-current="page"
                        class="cmp-navigation__item-link"
                        >Airmiles-Web</a
                      >
                    </li>
                  </ul>
                </nav>
              </div>
              <div class="languagenavigation">
                <nav class="cmp-languagenavigation">
                  <ul class="cmp-languagenavigation__group">
                    <li
                      class="cmp-languagenavigation__item cmp-languagenavigation__item--langcode-en cmp-languagenavigation__item--level-0"
                    >
                      <span class="cmp-languagenavigation__item-title" lang="en"
                        >United States</span
                      >
                      <ul class="cmp-languagenavigation__group">
                        <li
                          class="cmp-languagenavigation__item cmp-languagenavigation__item--langcode-en cmp-languagenavigation__item--level-1"
                        >
                          <a
                            class="cmp-languagenavigation__item-link"
                            href="/content/aem-airmiles-web/us/en.html"
                            hreflang="en"
                            lang="en"
                            rel="alternate"
                            title="English"
                            >English</a
                          >
                        </li>
                      </ul>
                    </li>
                  </ul>
                </nav>
              </div>
          </div>
        </header>
        <main class="container responsivegrid">
          <div class="cmp-container">
            <div id="root">${compMarkup}</div>
          </div>
        </main>
        <footer class="experiencefragment">
          <div class="cmp-experiencefragment">
            <div class="cmp-container">
              <div class="separator">
                <div class="cmp-separator">
                  <hr class="cmp-separator__horizontal-rule" />
                </div>
              </div>
              <div class="text">
                <div class="cmp-text">
                  <p>Copyright 2019, Airmiles-Web. All rights reserved.</p>
                </div>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
    <script
      type="text/javascript"
      src="/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-site.js"
    ></script>
    <script
      type="text/javascript"
      src="/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-base.js"
    ></script>
  </body>
</html>
`;

module.exports = staticIndexMarkup;
