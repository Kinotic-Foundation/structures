import { getBaseUrl } from '@/util/helpers'
import { ApolloClient, createHttpLink, InMemoryCache } from '@apollo/client/core'

// HTTP connection to the API
const httpLink = createHttpLink({
  // uri: 'http://localhost:8080/graphql',
  uri: `${getBaseUrl()}/graphql`
})

// Cache implementation
const cache = new InMemoryCache()

// Create the apollo client
export default new ApolloClient({
  link: httpLink,
  cache,
})