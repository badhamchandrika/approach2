'use strict';

const path                    = require('path');
const webpack                 = require('webpack');
const MiniCssExtractPlugin    = require('mini-css-extract-plugin');
const TSConfigPathsPlugin     = require('tsconfig-paths-webpack-plugin');
const CopyWebpackPlugin       = require('copy-webpack-plugin');
const { CleanWebpackPlugin }  = require('clean-webpack-plugin');

const SOURCE_ROOT = __dirname + '/src/main/webpack';
const CORPORATE='/corporate';
const OFFERS='/offers';
const SHELL='/shell';
const resolve = {
    extensions: ['.js', '.ts'],
    plugins: [new TSConfigPathsPlugin({
        configFile: './tsconfig.json'
    })]
};

module.exports = {
    resolve: resolve,
    entry: {
        //site: SOURCE_ROOT + '/site/main.ts'
        dependencies: SOURCE_ROOT +'/dependencies/main.ts',
        corporate_dependencies: SOURCE_ROOT + CORPORATE +'/dependencies/main.ts',
        corporate: SOURCE_ROOT + CORPORATE +'/site/main.ts',
        offers: SOURCE_ROOT + OFFERS +'/site/main.ts',
        shell: SOURCE_ROOT + SHELL +'/main.ts',
    },
    output: {
        filename: (chunkData) => {
            return chunkData.chunk.name === 'dependencies' ? 'clientlib-dependencies/[name].js' : 'clientlib-[name]-site/[name].js';
        },
        path: path.resolve(__dirname, 'dist')
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                exclude: /node_modules/,
                use: [
                    {
                        options: {
                            eslintPath: require.resolve('eslint'),
                        },
                        loader: require.resolve('eslint-loader'),
                    },
                    {
                        loader: 'ts-loader'
                    },
                    {
                        loader: 'glob-import-loader',
                        options: {
                            resolve: resolve
                        }
                    }
                ]
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'eslint-loader',
            },
            {
                test: /\.scss$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    {
                        loader: 'css-loader',
                        options: {
                            url: false
                        }
                    },
                    {
                        loader: 'postcss-loader',
                        options: {
                            plugins() {
                                return [
                                    require('autoprefixer')
                                ];
                            }
                        }
                    },
                    {
                        loader: 'sass-loader',
                        options: {
                            url: false
                        }
                    },
                    {
                        loader: 'glob-import-loader',
                        options: {
                            resolve: resolve
                        }
                    }
                ]
            }
        ]
    },
    plugins: [
        new CleanWebpackPlugin(),
        new webpack.NoEmitOnErrorsPlugin(),
        new MiniCssExtractPlugin({
            filename: 'clientlib-[name]-site/[name].css'
        }),
        new CopyWebpackPlugin([
            { from: path.resolve(__dirname, SOURCE_ROOT + '/dependencies/resources'), to: './clientlib-dependencies' },
            { from: path.resolve(__dirname, SOURCE_ROOT + CORPORATE +'/resources/'), to: './clientlib-corporate-site'},
            { from: path.resolve(__dirname, SOURCE_ROOT + OFFERS +'/resources/'), to: './clientlib-offers-site'},
            { from: path.resolve(__dirname, SOURCE_ROOT + SHELL +'/resources/'), to: './clientlib-shell-site'}
        ])
    ],
    stats: {
        assetsSort: 'chunks',
        builtAt: true,
        children: false,
        chunkGroups: true,
        chunkOrigins: true,
        colors: false,
        errors: true,
        errorDetails: true,
        env: true,
        modules: false,
        performance: true,
        providedExports: false,
        source: false,
        warnings: true
    }
};
