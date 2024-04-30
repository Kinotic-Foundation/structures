// @ts-nocheck
import {describe, it, expect} from 'vitest'
import { WebSocket } from 'ws'
import {Query} from '../src/index.js'

Object.assign(global, { WebSocket})


class ParentClass {

    public beHappy(): number{
        return 42
    }

}

class ChildClass extends ParentClass {

    @Query('Testing')
    public beHappy(): number {
        return 7
    }

}

describe('QueryDecorator', () => {

    it<LocalTestContext>('Test Query Decorator calls parent',
        async () => {
            let beepBop = new ChildClass()
            expect(beepBop.beHappy()).toEqual(7)
        }
    )

})
