import { type IDataSource, type Identifiable, type Page, Pageable } from '@kinotic/continuum-client'

export function createSimpleDataSource(items: Identifiable<string>[]): IDataSource<Identifiable<string>> {
  return {
    findAll: async (pageable: Pageable): Promise<Page<Identifiable<string>>> => {
      const start = pageable.pageNumber * pageable.pageSize
      const end = start + pageable.pageSize
      const pagedItems = items.slice(start, end)
      return {
        content: pagedItems,
        totalElements: items.length,
        totalPages: Math.ceil(items.length / pageable.pageSize),
        number: pageable.pageNumber,
        size: pageable.pageSize
      }
    },
    search: async (term: string, pageable: Pageable): Promise<Page<Identifiable<string>>> => {
      const filtered = items.filter(i =>
        i.id.toLowerCase().includes(term.toLowerCase()) ||
        (i.description?.toLowerCase() ?? '').includes(term.toLowerCase())
      )
      const start = pageable.pageNumber * pageable.pageSize
      const end = start + pageable.pageSize
      const pagedItems = filtered.slice(start, end)
      return {
        content: pagedItems,
        totalElements: filtered.length,
        totalPages: Math.ceil(filtered.length / pageable.pageSize),
        number: pageable.pageNumber,
        size: pageable.pageSize
      }
    }
  }
}
