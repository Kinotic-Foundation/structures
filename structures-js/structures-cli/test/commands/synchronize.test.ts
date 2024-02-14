import {expect, test} from '@oclif/test'

describe('synchronize', () => {
  test
    .stdout()
    .command(['synchronize', 'org.kinotic', '-e /Users/navid/workspace/git/continuum/structures/structures-js/structures-ui-example/src/domain'])
    .it('runs synchronize', ctx => {
      expect(ctx.stdout).to.contain('hello friend from oclif!')
    })
})
