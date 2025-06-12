import { ITaskGenerator } from "./ITaskGenerator"
import { ITask } from "./ITask"
import { ConnectionInfo, Continuum } from '@kinotic/continuum-client'
import { Structures, Structure, INamespaceService, IStructureService, IEntityService } from '@kinotic/structures-api'
import { Customer } from '../entity/domain/ecommerce/Customer'
import { Product } from '../entity/domain/ecommerce/Product'
import { ProductReview } from '../entity/domain/ecommerce/ProductReview'
import { Purchase } from '../entity/domain/ecommerce/Purchase'
import { EntityDefinitionLoader } from '../utils/EntityDefinitionLoader'
import { TestDataGenerator } from '../entity/domain/ecommerce/TestDataGenerator'
import { ObjectC3Type } from '@kinotic/continuum-idl'

export class CreateComplexStructuresTaskGenerator implements ITaskGenerator {
    private tasks: ITask[] = []
    private currentTaskIndex: number = 0
    private entityDefinitions: Map<string, ObjectC3Type> = new Map()
    private readonly connectionInfoSupplier: () => Promise<ConnectionInfo>
    private readonly namespaceService: INamespaceService
    private readonly structureService: IStructureService
    private customerService?: IEntityService<Customer>
    private productService?: IEntityService<Product>
    private reviewService?: IEntityService<ProductReview>
    private purchaseService?: IEntityService<Purchase>

    constructor(connectionInfoSupplier: () => Promise<ConnectionInfo>) {
        this.connectionInfoSupplier = connectionInfoSupplier
        this.namespaceService = Structures.getNamespaceService()
        this.structureService = Structures.getStructureService()
        this.initialize()
    }

    initialize(): void {
        // Initialize tasks
        this.tasks = [
            // Connect to Continuum
            {
                name: () => 'Connect to Continuum',
                execute: async () => {
                    const connectionInfo = await this.connectionInfoSupplier()
                    await Continuum.connect(connectionInfo)
                }
            },
            // Create namespace
            {
                name: () => 'Create Ecommerce Namespace',
                execute: async () => {
                    await this.namespaceService.createNamespaceIfNotExist('ecommerce', 'Ecommerce Domain')
                }
            },
            // Load entity definitions
            {
                name: () => 'Load Entity Definitions',
                execute: async () => {
                    const loader = new EntityDefinitionLoader(
                        'ecommerce',
                        '../entity/domain/ecommerce',
                        '../services/ecommerce/'
                    )
                    this.entityDefinitions = await loader.loadDefinitions()
                }
            },
            // Create Customer structure
            {
                name: () => 'Create Customer Structure',
                execute: async () => {
                    const structureId = 'ecommerce.customer'
                    const existingStructure = await this.structureService.findById(structureId)
                    
                    if (!existingStructure) {
                        const customerStructure = new Structure(
                            'ecommerce',
                            'Customer',
                            this.entityDefinitions.get('customer')!,
                            'Customer information and preferences'
                        )
                        const savedStructure = await this.structureService.create(customerStructure)
                        if (savedStructure.id) {
                            await this.structureService.publish(savedStructure.id)
                        }
                    } else {
                        console.log('Customer structure already exists, skipping creation')
                    }
                    
                    this.customerService = Structures.createEntityService('ecommerce', 'Customer')
                }
            },
            // Create Product structure
            {
                name: () => 'Create Product Structure',
                execute: async () => {
                    const structureId = 'ecommerce.product'
                    const existingStructure = await this.structureService.findById(structureId)
                    
                    if (!existingStructure) {
                        const productStructure = new Structure(
                            'ecommerce',
                            'Product',
                            this.entityDefinitions.get('product')!,
                            'Product catalog information'
                        )
                        const savedStructure = await this.structureService.create(productStructure)
                        if (savedStructure.id) {
                            await this.structureService.publish(savedStructure.id)
                        }
                    } else {
                        console.log('Product structure already exists, skipping creation')
                    }
                    
                    this.productService = Structures.createEntityService('ecommerce', 'Product')
                }
            },
            // Create ProductReview structure
            {
                name: () => 'Create ProductReview Structure',
                execute: async () => {
                    const structureId = 'ecommerce.productreview'
                    const existingStructure = await this.structureService.findById(structureId)
                    
                    if (!existingStructure) {
                        const reviewStructure = new Structure(
                            'ecommerce',
                            'ProductReview',
                            this.entityDefinitions.get('productreview')!,
                            'Product reviews and ratings'
                        )
                        const savedStructure = await this.structureService.create(reviewStructure)
                        if (savedStructure.id) {
                            await this.structureService.publish(savedStructure.id)
                        }
                    } else {
                        console.log('ProductReview structure already exists, skipping creation')
                    }
                    
                    this.reviewService = Structures.createEntityService('ecommerce', 'ProductReview')
                }
            },
            // Create Purchase structure
            {
                name: () => 'Create Purchase Structure',
                execute: async () => {
                    const structureId = 'ecommerce.purchase'
                    const existingStructure = await this.structureService.findById(structureId)
                    
                    if (!existingStructure) {
                        const purchaseStructure = new Structure(
                            'ecommerce',
                            'Purchase',
                            this.entityDefinitions.get('purchase')!,
                            'Purchase orders and transactions'
                        )
                        const savedStructure = await this.structureService.create(purchaseStructure)
                        if (savedStructure.id) {
                            await this.structureService.publish(savedStructure.id)
                        }
                    } else {
                        console.log('Purchase structure already exists, skipping creation')
                    }
                    
                    this.purchaseService = Structures.createEntityService('ecommerce', 'Purchase')
                }
            },
            // Generate and save test data
            {
                name: () => 'Generate and Save Test Data',
                execute: async () => {
                    if (!this.customerService || !this.productService || !this.reviewService || !this.purchaseService) {
                        throw new Error('Entity services not initialized')
                    }

                    // Generate test data
                    const { customers, products, reviews, purchases } = TestDataGenerator.generateTestData(20)

                    // Save customers
                    await this.customerService.bulkSave(customers)
                    await this.customerService.syncIndex()

                    // Save products
                    await this.productService.bulkSave(products)
                    await this.productService.syncIndex()

                    // Save reviews
                    await this.reviewService.bulkSave(reviews)
                    await this.reviewService.syncIndex()

                    // Save purchases
                    await this.purchaseService.bulkSave(purchases)
                    await this.purchaseService.syncIndex()

                    // Verify counts
                    const customerCount = await this.customerService.count()
                    const productCount = await this.productService.count()
                    const reviewCount = await this.reviewService.count()
                    const purchaseCount = await this.purchaseService.count()

                    console.log(`Generated and saved test data:
                        - ${customerCount} customers
                        - ${productCount} products
                        - ${reviewCount} reviews
                        - ${purchaseCount} purchases`)
                }
            },
            // Disconnect from Continuum
            {
                name: () => 'Disconnect from Continuum',
                execute: async () => {
                    await Continuum.disconnect()
                }
            }
        ]
    }

    getNextTask(): ITask {
        if (this.currentTaskIndex >= this.tasks.length) {
            throw new Error('No more tasks available')
        }
        return this.tasks[this.currentTaskIndex++]
    }

    hasMoreTasks(): boolean {
        return this.currentTaskIndex < this.tasks.length
    }
}