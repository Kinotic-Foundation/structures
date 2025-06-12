import { ITask } from "../ITask"
import { IEntityService } from '@kinotic/structures-api'
import { Structures } from '@kinotic/structures-api'
import { Patient } from '../../entity/domain/health/Patient'
import { Provider } from '../../entity/domain/health/Provider'
import { Appointment } from '../../entity/domain/health/Appointment'
import { Diagnosis } from '../../entity/domain/health/Diagnosis'
import { Treatment } from '../../entity/domain/health/Treatment'
import { Prescription } from '../../entity/domain/health/Prescription'
import { EntityDefinitionLoader } from '../../utils/EntityDefinitionLoader'
import { CreateStructureTaskBuilder } from './CreateStructureTaskBuilder'
import { createStructureTaskBuilder } from './CreateStructureTaskBuilder'
import { ObjectC3Type } from '@kinotic/continuum-idl'
import { TestDataGenerator } from '../../entity/domain/health/TestDataGenerator'
import path from 'path'

export class HealthTaskFactory {
    private readonly namespace = 'healthcare'
    private readonly taskBuilder: CreateStructureTaskBuilder
    private entityDefinitions: Map<string, ObjectC3Type> = new Map()
    private patientService?: IEntityService<Patient>
    private providerService?: IEntityService<Provider>
    private appointmentService?: IEntityService<Appointment>
    private diagnosisService?: IEntityService<Diagnosis>
    private treatmentService?: IEntityService<Treatment>
    private prescriptionService?: IEntityService<Prescription>

    constructor() {
        this.taskBuilder = createStructureTaskBuilder()
    }

    getTasks(): ITask[] {
        return [
            // Create namespace first
            {
                name: () => 'Create Health Namespace',
                execute: async () => {
                    await Structures.getNamespaceService().createNamespaceIfNotExist(this.namespace, 'Healthcare Domain')
                }
            },
            // Then load entity definitions
            {
                name: () => 'Load Health Entity Definitions',
                execute: async () => {
                    const loader = new EntityDefinitionLoader(
                        this.namespace,
                        path.join(__dirname, '../../entity/domain/health'),
                        path.join(__dirname, '../../services/health')
                    )
                    this.entityDefinitions = await loader.loadDefinitions()
                    console.log('Loaded', this.entityDefinitions.size, 'entity definitions')
                }
            },
            // Then create structures
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Patient',
                description: 'Patient information and medical history',
                entityDefinitionSupplier: () => this.entityDefinitions.get('patient')!,
                onServiceCreated: (service: IEntityService<any>) => {
                    this.patientService = service as IEntityService<Patient>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Provider',
                description: 'Healthcare provider information',
                entityDefinitionSupplier: () => this.entityDefinitions.get('provider')!,
                onServiceCreated: (service: IEntityService<any>) => {
                    this.providerService = service as IEntityService<Provider>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Appointment',
                description: 'Medical appointments and scheduling',
                entityDefinitionSupplier: () => this.entityDefinitions.get('appointment')!,
                onServiceCreated: (service: IEntityService<any>) => {
                    this.appointmentService = service as IEntityService<Appointment>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Diagnosis',
                description: 'Medical diagnoses and conditions',
                entityDefinitionSupplier: () => this.entityDefinitions.get('diagnosis')!,
                onServiceCreated: (service: IEntityService<any>) => {
                    this.diagnosisService = service as IEntityService<Diagnosis>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Treatment',
                description: 'Medical treatments and procedures',
                entityDefinitionSupplier: () => this.entityDefinitions.get('treatment')!,
                onServiceCreated: (service: IEntityService<any>) => {
                    this.treatmentService = service as IEntityService<Treatment>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Prescription',
                description: 'Medical prescriptions and medications',
                entityDefinitionSupplier: () => this.entityDefinitions.get('prescription')!,
                onServiceCreated: (service: IEntityService<any>) => {
                    this.prescriptionService = service as IEntityService<Prescription>
                }
            }),
            // Generate and save test data
            {
                name: () => 'Generate Health Domain Test Data',
                execute: async () => {
                    if (!this.patientService || !this.providerService || 
                        !this.diagnosisService || !this.treatmentService || 
                        !this.appointmentService || !this.prescriptionService) {
                        throw new Error('Required services not initialized')
                    }

                    const { patients, providers, diagnoses, treatments, appointments, prescriptions } = 
                        TestDataGenerator.generateTestData(20)

                    // Save all entities in sequence to maintain referential integrity
                    for (const patient of patients) {
                        await this.patientService.save(patient)
                    }

                    for (const provider of providers) {
                        await this.providerService.save(provider)
                    }

                    for (const diagnosis of diagnoses) {
                        await this.diagnosisService.save(diagnosis)
                    }

                    for (const treatment of treatments) {
                        await this.treatmentService.save(treatment)
                    }

                    for (const prescription of prescriptions) {
                        await this.prescriptionService.save(prescription)
                    }

                    for (const appointment of appointments) {
                        await this.appointmentService.save(appointment)
                    }
                }
            }
        ]
    }
} 