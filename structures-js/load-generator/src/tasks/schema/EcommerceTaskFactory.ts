import { ITask } from "../ITask"
import { IEntityService } from '@kinotic/structures-api'
import { Structures } from '@kinotic/structures-api'
import { Customer } from '../../entity/domain/ecommerce/Customer'
import { Product } from '../../entity/domain/ecommerce/Product'
import { ProductReview } from '../../entity/domain/ecommerce/ProductReview'
import { Purchase } from '../../entity/domain/ecommerce/Purchase'
import { TestDataGenerator } from '../../entity/domain/ecommerce/TestDataGenerator'
import { EntityDefinitionLoader } from '../../utils/EntityDefinitionLoader'
import { CreateStructureTaskBuilder } from './CreateStructureTaskBuilder'
import { ObjectC3Type } from '@kinotic/continuum-idl'

export class EcommerceTaskFactory {
    private readonly namespace = 'ecommerce'
    private readonly taskBuilder: CreateStructureTaskBuilder
    private entityDefinitions: Map<string, ObjectC3Type> = new Map()
    private customerService?: IEntityService<Customer>
    private productService?: IEntityService<Product>
    private reviewService?: IEntityService<ProductReview>
    private purchaseService?: IEntityService<Purchase>

    constructor() {
        this.taskBuilder = CreateStructureTaskBuilder.getInstance()
    }

    getTasks(): ITask[] {
        return [
            // Create namespace first
            {
                name: () => 'Create Ecommerce Namespace',
                execute: async () => {
                    await Structures.getNamespaceService().createNamespaceIfNotExist(this.namespace, 'Ecommerce Domain')
                }
            },
            // Then load entity definitions
            {
                name: () => 'Load Ecommerce Entity Definitions',
                execute: async () => {
                    const loader = new EntityDefinitionLoader(
                        this.namespace,
                        '../../entity/domain/ecommerce',
                        '../../services/ecommerce/'
                    )
                    this.entityDefinitions = await loader.loadDefinitions()
                }
            },
            // Then create structures
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Customer',
                description: 'Customer information and preferences',
                entityDefinition: this.entityDefinitions.get('customer')!,
                onServiceCreated: (service) => {
                    this.customerService = service as IEntityService<Customer>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Product',
                description: 'Product catalog information',
                entityDefinition: this.entityDefinitions.get('product')!,
                onServiceCreated: (service) => {
                    this.productService = service as IEntityService<Product>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'ProductReview',
                description: 'Product reviews and ratings',
                entityDefinition: this.entityDefinitions.get('productreview')!,
                onServiceCreated: (service) => {
                    this.reviewService = service as IEntityService<ProductReview>
                }
            }),
            this.taskBuilder.buildTask({
                namespace: this.namespace,
                name: 'Purchase',
                description: 'Purchase orders and transactions',
                entityDefinition: this.entityDefinitions.get('purchase')!,
                onServiceCreated: (service) => {
                    this.purchaseService = service as IEntityService<Purchase>
                }
            }),
            // Generate and save test data
            {
                name: () => 'Generate and Save Ecommerce Test Data',
                execute: async () => {
                    if (!this.customerService || !this.productService || !this.reviewService || !this.purchaseService) {
                        throw new Error('Entity services not initialized')
                    }

                    const { customers, products, reviews, purchases } = TestDataGenerator.generateTestData(20)

                    await this.customerService.bulkSave(customers)
                    await this.customerService.syncIndex()

                    await this.productService.bulkSave(products)
                    await this.productService.syncIndex()

                    await this.reviewService.bulkSave(reviews)
                    await this.reviewService.syncIndex()

                    await this.purchaseService.bulkSave(purchases)
                    await this.purchaseService.syncIndex()

                    const customerCount = await this.customerService.count()
                    const productCount = await this.productService.count()
                    const reviewCount = await this.reviewService.count()
                    const purchaseCount = await this.purchaseService.count()

                    console.log(`Generated and saved ecommerce test data:
                        - ${customerCount} customers
                        - ${productCount} products
                        - ${reviewCount} reviews
                        - ${purchaseCount} purchases`)
                }
            }
        ]
    }
} 