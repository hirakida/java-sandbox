const path = require('path');
const {VueLoaderPlugin} = require('vue-loader');
const DEV = process.argv.includes('development');

module.exports = {
  devtool: DEV ? 'cheap-module-eval-source-map' : false,
  entry: './src/main/js/app.js',
  output: {
    path: path.resolve(__dirname, 'build/resources/main/static'),
    filename: '[name].bundle.js'
  },
  optimization: {
    splitChunks: {
      cacheGroups: {
        vendors: {
          test: /node_modules/,
          name: "vendors",
          chunks: "initial",
          enforce: true
        }
      }
    }
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        use: {
          loader: 'vue-loader'
        }
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
            plugins: [require('@babel/plugin-proposal-object-rest-spread')]
          }
        }
      }
    ]
  },
  plugins: [new VueLoaderPlugin()]
};
