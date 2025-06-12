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
import { ObjectC3Type } from '@kinotic/continuum-idl'
import { TestDataGenerator } from '../../entity/domain/health/TestDataGenerator'

export class HealthTaskFactory {
    private readonly namespace = 'health'
    private readonly taskBuilder: CreateStructureTaskBuilder
    private entityDefinitions: Map<string, ObjectC3Type> = new Map()
    private patientService?: IEntityService<Patient>
    private providerService?: IEntityService<Provider>
    private appointmentService?: IEntityService<Appointment>
    private diagnosisService?: IEntityService<Diagnosis>
    private treatmentService?: IEntityService<Treatment>
    private prescriptionService?: IEntityService<Prescription>

    constructor() {
        this.taskBuilder = CreateStructureTaskBuilder.getInstance()
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
                        '../../entity/domain/health',
                        '../../services/health/'
                    )
                    this.entityDefinitions = await loader.loadDefinitions()
                }
            },
            // Then create structures
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Patient',
                description: 'Patient information and medical history',
                entityDefinition: this.entityDefinitions.get('patient')!,
                onServiceCreated: (service) => {
                    this.patientService = service as IEntityService<Patient>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Provider',
                description: 'Healthcare provider information',
                entityDefinition: this.entityDefinitions.get('provider')!,
                onServiceCreated: (service) => {
                    this.providerService = service as IEntityService<Provider>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Appointment',
                description: 'Medical appointments and scheduling',
                entityDefinition: this.entityDefinitions.get('appointment')!,
                onServiceCreated: (service) => {
                    this.appointmentService = service as IEntityService<Appointment>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Diagnosis',
                description: 'Medical diagnoses and conditions',
                entityDefinition: this.entityDefinitions.get('diagnosis')!,
                onServiceCreated: (service) => {
                    this.diagnosisService = service as IEntityService<Diagnosis>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Treatment',
                description: 'Medical treatments and procedures',
                entityDefinition: this.entityDefinitions.get('treatment')!,
                onServiceCreated: (service) => {
                    this.treatmentService = service as IEntityService<Treatment>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Prescription',
                description: 'Medical prescriptions and medications',
                entityDefinition: this.entityDefinitions.get('prescription')!,
                onServiceCreated: (service) => {
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