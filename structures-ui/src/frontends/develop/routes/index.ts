import { RouteConfig } from 'vue-router'

const developRoutes: RouteConfig[] = [
    {
        path: '/dev',
        component: () => import(/* webpackChunkName: "frontendLayout" */'@/frontends/continuum/layouts/FrontendLayout.vue'),
        meta: {
            isFrontend: true,
            icon: 'fa-code',
            title: 'Develop'
        },
        children: [
            {
                path: '/dev-home', component: () => import(/* webpackChunkName: "developFrontEnd" */'@/frontends/develop/pages/ActiveSessions.vue'),
                meta: {
                    icon: 'fa-home', // font awesome icon
                    title: 'Active Sessions'
                }
            },
            {
                path: '/dev-eventtest', component: () => import(/* webpackChunkName: "developFrontEnd" */'@/frontends/develop/pages/EventTest.vue'),
                meta: {
                    icon: 'fa-paper-plane',
                    title: 'Event Test'
                }
            },
            {
                path: '/dev-kafka-consumers', component: () => import(/* webpackChunkName: "developFrontEnd" */'@/frontends/develop/pages/KafkaConsumers.vue'),
                meta: {
                    icon: 'fa-project-diagram',
                    title: 'Kafka Consumers'
                }
            }
        ]
    }
]

export default developRoutes
