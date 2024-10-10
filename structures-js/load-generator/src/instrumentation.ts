/*instrumentation.ts*/
import {OTLPTraceExporter} from '@opentelemetry/exporter-trace-otlp-grpc'
import { NodeSDK } from '@opentelemetry/sdk-node';
import {ConsoleSpanExporter, SpanExporter} from '@opentelemetry/sdk-trace-node'
import { Resource } from '@opentelemetry/resources';
import {
    ATTR_SERVICE_NAME,
    ATTR_SERVICE_VERSION,
} from '@opentelemetry/semantic-conventions';
import info from '../package.json' assert {type: 'json'}
import {OtelConfig, OtelExporterType} from '@/config/OtelConfig.js'

const otelConfig = OtelConfig.fromEnv()
console.log('Otel Config:')
otelConfig.print()

let spanExporter: SpanExporter = new ConsoleSpanExporter();
if(otelConfig.exporterType === OtelExporterType.OTLP){
    spanExporter = new OTLPTraceExporter({
                                            url: otelConfig.otelEndpoint
                                         })
}

export const nodeSdk = new NodeSDK({
                            resource: new Resource({
                                                       [ATTR_SERVICE_NAME]: 'structures.load-generator',
                                                       [ATTR_SERVICE_VERSION]: info.version,
                                                   }),
                            traceExporter: spanExporter,
                        })

nodeSdk.start()
