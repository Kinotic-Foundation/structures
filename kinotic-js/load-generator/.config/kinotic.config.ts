import type { KinoticProjectConfig } from '@kinotic-ai/management-api'

const config: KinoticProjectConfig = {
  organizationId: "test-org",
  applicationId: "load-testing",
  entitiesPaths: [
    {
      path: "src/entity/domain",
      repositoryPath: "src/repository",
      mirrorFolderStructure: true
    }
  ],
  fileExtensionForImports: ".js",
  validate: false
}

export default config
