import type { KinoticProjectConfig } from '@kinotic-ai/management-api'

const config: KinoticProjectConfig = {
  organizationId: "test-org",
  applicationId: "e2e-tests",
  entitiesPaths: [
    {
      path: "test/domain",
      repositoryPath: "test/repository",
      mirrorFolderStructure: true
    }
  ],
  fileExtensionForImports: ".js",
  validate: false
}

export default config
