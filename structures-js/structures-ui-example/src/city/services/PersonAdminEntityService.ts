import {IAdminEntitiesService, Query, type TenantSelection} from '@kinotic/structures-api'
import { BasePersonAdminEntityService } from './generated/BasePersonAdminEntityService.js'
import {CityCount} from './PersonEntityService.js'

/**
 * Admin Service for interacting with Person entities
 * This class was generated by the structures cli
 */
export class PersonAdminEntityService extends BasePersonAdminEntityService {

  constructor(adminEntitiesService?: IAdminEntitiesService) {
    super(adminEntitiesService)
  }

    /**
     * Get a count of all people in a given city
     * @param city The city name to get the count for
     */
    @Query('select count(firstName) as count, address.city as city from "struct_city.person" where address.city = ? group by address.city')
    public async adminCountByCity(city: string, tenantSelection: TenantSelection): Promise<CityCount[]> {
      /** Autogenerated code, changes will be overwritten. **/
      const parameters = [
        {key: 'city', value: city},
        {key: 'tenantSelection', value: tenantSelection}
      ]
      return this.namedQuery('adminCountByCity', parameters)
    }

}
