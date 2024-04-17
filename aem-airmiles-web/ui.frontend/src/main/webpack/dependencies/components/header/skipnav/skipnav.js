(() => {
  const skipAnchor = document.querySelector(".skipnav-anchor");
  const _main = document.querySelector("main");
  if(!_main) {return;}
  const mainId = _main.querySelector('div');
  if(!skipAnchor || !mainId) {return;}
  const _id = mainId.getAttribute('id');
  const _sid = skipAnchor.getAttribute('sid');
  skipAnchor.setAttribute('href', `#${_sid ? _sid : _id}`);
})();
