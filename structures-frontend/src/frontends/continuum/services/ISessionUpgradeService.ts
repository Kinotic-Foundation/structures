import {Continuum, IServiceProxy} from '@kinotic/continuum-client'

export interface ISessionUpgradeService {

    upgradeSession(upgradeId: string, sessionId: string): Promise<void>

}

export class SessionUpgradeService implements ISessionUpgradeService {

    protected serviceProxy: IServiceProxy

    constructor() {
        this.serviceProxy = Continuum.serviceProxy('continuum.cli.SessionUpgradeService')
    }

    public async upgradeSession(upgradeId: string, sessionId: string): Promise<void> {
        await this.serviceProxy.invoke('upgradeSession', [sessionId], upgradeId)
    }
}


export const SESSION_UPGRADE_SERVICE: ISessionUpgradeService = new SessionUpgradeService()
