import { EntityService, IEntitiesService } from '@kinotic/structures-api'
import { AnimalDen } from '../../domain/Animal.js'
import {Bear,
Cat,
Dog,
Rabbit} from '../../domain/Animal.js'


/**
 * Base Service for interacting with AnimalDen entities
 * This class was generated by the Structures CLI. And will be overwritten if the CLI is run again.
 */
export class BaseAnimalDenEntityService extends EntityService<AnimalDen> {

  private readonly shouldValidate: boolean

  constructor(shouldValidate: boolean = true, entitiesService?: IEntitiesService) {
    super('animal', 'AnimalDen', entitiesService)
    this.shouldValidate = shouldValidate
  }

  protected async beforeSaveOrUpdate(entity: AnimalDen): Promise<AnimalDen> {
    if (this.shouldValidate) {
      return this.validate(entity)
    } else {
      return entity
    }
  }

  protected async beforeBulkSaveOrUpdate(entities: AnimalDen[]): Promise<AnimalDen[]> {
    if (this.shouldValidate) {
      const validatedEntities: AnimalDen[] = []
      for (let entity of entities) {
        validatedEntities.push(this.validate(entity))
      }
      return validatedEntities
    } else {
      return entities
    }
  }

  validate(entity: AnimalDen): AnimalDen {
    let ret: any
    if (entity) {
      ret = (ret ? ret : {})
      ret.id = entity.id
      let entityLivesHereI1 = entity.livesHere as Bear
      let retLivesHereO1 = ret.livesHere
      if (entityLivesHereI1) {
        retLivesHereO1 = (retLivesHereO1 ? retLivesHereO1 : {})
        retLivesHereO1.type = entityLivesHereI1.type
        retLivesHereO1.species = entityLivesHereI1.species
      }
      ret.livesHere = retLivesHereO1
      let entityLivesHereI2 = entity.livesHere as Cat
      let retLivesHereO2 = ret.livesHere
      if (entityLivesHereI2) {
        retLivesHereO2 = (retLivesHereO2 ? retLivesHereO2 : {})
        retLivesHereO2.type = entityLivesHereI2.type
        retLivesHereO2.species = entityLivesHereI2.species
      }
      ret.livesHere = retLivesHereO2
      let entityLivesHereI3 = entity.livesHere as Dog
      let retLivesHereO3 = ret.livesHere
      if (entityLivesHereI3) {
        retLivesHereO3 = (retLivesHereO3 ? retLivesHereO3 : {})
        retLivesHereO3.type = entityLivesHereI3.type
        retLivesHereO3.species = entityLivesHereI3.species
      }
      ret.livesHere = retLivesHereO3
      let entityLivesHereI4 = entity.livesHere as Rabbit
      let retLivesHereO4 = ret.livesHere
      if (entityLivesHereI4) {
        retLivesHereO4 = (retLivesHereO4 ? retLivesHereO4 : {})
        retLivesHereO4.type = entityLivesHereI4.type
        retLivesHereO4.species = entityLivesHereI4.species
      }
      ret.livesHere = retLivesHereO4
      ret.madeOf = entity.madeOf
    }

    return ret
  }

}