
// import all services modules here
// TODO: is the order important, if so how to best handle circular deps?
// import './main/services'
// import './admin/services'
// import './develop/services'
import './structures-admin/services/index.ts'
import {GRIND_SERVICE_FACTORY, IGrindServiceFactory} from './continuum/services'


export namespace StructuresServices {

    export function getGrindServiceFactory(): IGrindServiceFactory {
        return GRIND_SERVICE_FACTORY
    }

}
