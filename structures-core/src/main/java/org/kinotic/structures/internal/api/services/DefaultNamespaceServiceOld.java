package org.kinotic.structures.internal.api.services;

public class DefaultNamespaceServiceOld {

//    private final ObjectMapper mapper = new ObjectMapper();
//    private RestHighLevelClient highLevelClient;
//    private StructureServiceInternal structureService;
//
//    public DefaultNamespaceServiceOld(RestHighLevelClient highLevelClient, StructureServiceInternal structureService){
//        this.highLevelClient = highLevelClient;
//        this.structureService = structureService;
//    }
//
//    @PostConstruct
//    void init() {
//        createNamespaceIndex();
//    }
//
//    @Override
//    public Namespace save(Namespace namespace) throws AlreadyExistsException, IOException {
//        if(namespace.getName() == null || namespace.getName().isBlank()){
//            throw new IllegalArgumentException("Namespace must provide proper name.");
//        }
//        Optional<Namespace> alreadyCreated = getNamespace(namespace.getName());
//
//        IndexRequest request = new IndexRequest("namespace");
//        if(alreadyCreated.isPresent() && !Objects.equals(alreadyCreated.get().getUpdated(), namespace.getUpdated())){
//            if(namespace.getUpdated() == 0){
//                throw new AlreadyExistsException("Namespace name must be unique, '"+namespace.getName()+"' already exists.");
//            }else{
//                throw new OptimisticLockingFailureException("Attempting to update a Namespace, but out of sync with database; please re-fetch from database and try again");
//            }
//        }else if(alreadyCreated.isEmpty()){
//            request.create(true);
//            namespace.setUpdated(System.currentTimeMillis());
//        }else{
//            // version type field - updating
//            request.create(false);
//            namespace.setUpdated(System.currentTimeMillis());
//        }
//
//        request.id(namespace.getName());
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
//
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        builder.field("name", namespace.getName().trim());
//        builder.field("description", namespace.getDescription().trim());
//        builder.field("updated", namespace.getUpdated());
//        builder.endObject();
//
//        request.source(builder);
//
//        // FIXME: Need to handle exceptions and edge cases.
//        highLevelClient.index(request, RequestOptions.DEFAULT);
//        return namespace;
//    }
//
//    @Override
//    public Optional<Namespace> getNamespace(String namespace) throws IOException {
//
//        GetResponse response = highLevelClient.get(new GetRequest("namespace").id(namespace), RequestOptions.DEFAULT);
//        Namespace ret = null;
//        if (response.isExists()) {
//            ret = EsHighLevelClientUtil.getTypeFromBytesReference(response.getSourceAsBytesRef(), Namespace.class);
//        }
//
//        return Optional.ofNullable(ret);
//    }
//
//    @Override
//    public SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
//        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
//        return getNamespaces(builder);
//    }
//
//    @Override
//    public SearchHits getAllNamespaceLike(String namespaceLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
//        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
//        builder.postFilter(QueryBuilders.wildcardQuery("name", namespaceLike));
//        return getNamespaces(builder);
//    }
//
//    @Override
//    public void delete(String namespace) throws IOException {
//        Optional<Namespace> namespaceOptional = getNamespace(namespace.trim());
//
//        if(namespaceOptional.isPresent()){
//            Namespace toBeDeleted = namespaceOptional.get();
//
//            Structures response = structureService.getAllNamespaceEquals(toBeDeleted.getName(), 100, 0, "name", true);
//
//            if(response.getTotalElements() > 0){
//                throw new IllegalStateException("Attempting to delete a Namespace that is still attached to a Structure, you must delete all references before deleting this Namespace");
//            }
//
//            DeleteRequest request = new DeleteRequest("namespace");
//            request.id(toBeDeleted.getName());
//            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
//
//            // FIXME: Need to handle exceptions and edge cases.
//            highLevelClient.delete(request, RequestOptions.DEFAULT);
//        }else{
//            // what to do here? if anything.
//        }
//    }
//
//    @Override
//    public void createNamespaceIndex() {
//        try {
//            if(!highLevelClient.indices().exists(new GetIndexRequest("namespace"), RequestOptions.DEFAULT)){
//                HashMap<String, Object> settings = new HashMap<>();
//                settings.put("index.number_of_shards", 5);
//                settings.put("index.number_of_replicas", 2);
//                settings.put("index.refresh_interval", "1s");
//                settings.put("index.store.type", "fs");
//
//                CreateIndexRequest indexRequest = new CreateIndexRequest("namespace");
//                indexRequest.mapping("{\"dynamic\":\"strict\",\"properties\":{\"name\":{\"type\":\"keyword\"},\"description\":{\"type\":\"text\"},\"updated\":{\"type\":\"date\",\"format\":\"epoch_millis\"}}}", XContentType.JSON);
//                indexRequest.settings(settings);
//                highLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException("We were not able to check for 'namespace' existence or create 'namespace' index.", e);
//        }
//    }
//
//    private SearchHits getNamespaces(SearchSourceBuilder builder) throws IOException {
//        SearchResponse response = highLevelClient.search(new SearchRequest("namespace").source(builder), RequestOptions.DEFAULT);
//        return response.getHits();
//    }
}
