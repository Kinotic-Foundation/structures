const TerserPlugin = require('terser-webpack-plugin')

module.exports = {
    devServer: {
        host: '127.0.0.1',
        port: 8080,
        open: false
    },

    lintOnSave: false,

    pluginOptions: {
        electronBuilder: {
            // options placed here will be merged with default configuration and passed to electron-builder
            nodeIntegration: true
        }
    },
    // chainWebpack: config => {
    //     config.module
    //         .rule('comlink')
    //         .test(/\.worker\.(js|ts)$/i)
    //         .use('comlink-loader')
    //         .loader('comlink-loader')
    //         .tap(options => {
    //             return {
    //                 singleton: true
    //             }
    //         })
    //         .end()
    // },

    // added this, so we can easily access source files from the browser
    configureWebpack: (config) => {
        if (process.env.NODE_ENV === 'development') {
            config.devtool = 'source-map';
            config.output.devtoolFallbackModuleFilenameTemplate = 'webpack:///[resource-path]?[hash]';
            config.output.devtoolModuleFilenameTemplate = info => {
                const isVue = info.resourcePath.match(/\.vue$/);
                const isScript = info.query.match(/type=script/);
                const hasModuleId = info.moduleId !== '';

                // Detect generated files, filter as webpack-generated
                if (
                    // Must result from vue-loader
                    isVue
                    // Must not be 'script' files (enough for chrome), or must have moduleId (firefox)
                    && (!isScript || hasModuleId)
                ) {
                    let pathParts = info.resourcePath.split('/');
                    const baseName = pathParts[pathParts.length - 1];
                    // prepend 'generated-' to filename as well, so it's easier to find desired files via Ctrl+P
                    pathParts.splice(-1,1,`generated-${baseName}`);
                    return `webpack-generated:///${pathParts.join('/')}?${info.hash}`;
                }

                // If not generated, filter as sources://
                return `sources://${info.resourcePath}`;
            }
        }else if(process.env.NODE_ENV === 'production'){

            config.optimization.minimizer[0].options.terserOptions.keep_classnames = true
            config.optimization.minimizer[0].options.terserOptions.keep_fnames = true
            config.optimization.minimizer[0].options.terserOptions.mangle.reserved = ['eventBus', 'serviceRegistry']

        }
    }
}
