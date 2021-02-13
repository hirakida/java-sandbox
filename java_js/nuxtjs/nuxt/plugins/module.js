const path = require('path');

module.exports = function nuxtBootstrapVue(moduleOptions) {
  this.addPlugin(path.resolve(__dirname, 'plugin.js'))
};
