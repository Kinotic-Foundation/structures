package org.kinotic.structures.insights;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.insights.InsightRequest;
import org.kinotic.structures.api.domain.insights.InsightResponse;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.insights.DataInsightsService;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

/**
 * Integration test for DataInsightsService using real Spring services.
 * This test follows the same pattern as EntityCrudTests, using actual
 * Spring dependencies instead of mocks.
 * 
 * <p><strong>Running with OpenAI:</strong></p>
 * <p>To run these tests with actual OpenAI integration, set the environment variable:</p>
 * <pre>OPENAI_API_KEY=your-actual-openai-api-key</pre>
 * 
 * <p><strong>Running without OpenAI:</strong></p>
 * <p>Tests will be automatically skipped if no valid OpenAI API key is configured.
 * This allows the tests to run in CI/CD environments without requiring API keys.</p>
 */
@SpringBootTest
public class DataInsightsServiceTest extends ElasticsearchTestBase {

    private static final Logger log = LoggerFactory.getLogger(DataInsightsServiceTest.class);

    @Autowired
    private DataInsightsService dataInsightsService;
    
    @Autowired
    private EntitiesService entitiesService;
    
    @Value("${spring.ai.openai.api-key:#{null}}")
    private String openAiApiKey;

    @Test
    public void testAnalyzeAndVisualizeWithBasicQuery() {
        // Skip test if OpenAI API key is not configured
        Assumptions.assumeTrue(openAiApiKey != null && !"test-key-placeholder".equals(openAiApiKey), 
                             "OpenAI API key not configured - skipping AI insights test");
        // Create test structure and data using the same pattern as EntityCrudTests
        StructureAndPersonHolder holder = createAndVerify(50, true, 
            new DefaultEntityContext(new DummyParticipant("testTenant", "testUser")), 
            "_datainsightstest");

        assertNotNull(holder);
        assertNotNull(holder.getStructure());
        assertTrue(holder.getPersons().size() > 0);

        // Sync the index to ensure data is searchable
        EntityContext context = new DefaultEntityContext(new DummyParticipant("testTenant", "testUser"));
        entitiesService.syncIndex(holder.getStructure().getId(), context).join();

        // Create an insight request for analyzing the test data
        InsightRequest request = new InsightRequest();
        request.setQuery("Show me a summary of the people data with their demographics");
        request.setApplicationId(holder.getStructure().getApplicationId());
        request.setFocusStructureId(holder.getStructure().getId());

        // Test the analyzeAndVisualize method
        DummyParticipant participant = new DummyParticipant("testTenant", "testUser");
        CompletableFuture<InsightResponse> analysisResult = dataInsightsService.analyzeAndVisualize(request, participant);

        // Verify the response using StepVerifier
        StepVerifier.create(reactor.core.publisher.Mono.fromFuture(analysisResult))
                .expectNextMatches(response -> {
                    // Verify basic properties of the response
                    boolean isValid = response != null &&
                                    response.getAnalysisId() != null &&
                                    response.getOriginalQuery() != null &&
                                    response.getAnalysisSummary() != null &&
                                    response.getGeneratedComponent() != null &&
                                    response.getStatus() != null;

                    if (isValid) {
                        log.info("AI Analysis completed successfully:");
                        log.info("- Analysis ID: {}", response.getAnalysisId());
                        log.info("- Status: {}", response.getStatus());
                        log.info("- Summary: {}", response.getAnalysisSummary());
                        
                        if (response.getGeneratedComponent() != null) {
                            log.info("- Generated React Component: {} characters", 
                                   response.getGeneratedComponent().getReactCode() != null ? 
                                   response.getGeneratedComponent().getReactCode().length() : 0);
                        }
                    } else {
                        log.error("AI Analysis response validation failed: {}", response);
                    }

                    return isValid;
                })
                .verifyComplete();
    }

    @Test
    public void testAnalyzeAndVisualizeWithSpecificVisualization() {
        // Skip test if OpenAI API key is not configured
        Assumptions.assumeTrue(openAiApiKey != null && !"test-key-placeholder".equals(openAiApiKey), 
                             "OpenAI API key not configured - skipping AI insights test");
        // Create test structure and data
        StructureAndPersonHolder holder = createAndVerify(10, true, 
            new DefaultEntityContext(new DummyParticipant("testTenant2", "testUser2")), 
            "_datainsightstest2");

        assertNotNull(holder);
        
        // Sync the index
        EntityContext context = new DefaultEntityContext(new DummyParticipant("testTenant2", "testUser2"));
        entitiesService.syncIndex(holder.getStructure().getId(), context).join();

        // Create a more specific insight request
        InsightRequest request = new InsightRequest();
        request.setQuery("Create a bar chart showing the distribution of people by age groups");
        request.setApplicationId(holder.getStructure().getApplicationId());
        request.setFocusStructureId(holder.getStructure().getId());
        request.setPreferredVisualization("bar");
        request.setAdditionalContext("Group ages into ranges like 18-30, 31-50, 51+");

        // Test the analyzeAndVisualize method
        DummyParticipant participant = new DummyParticipant("testTenant2", "testUser2");
        CompletableFuture<InsightResponse> analysisResult = dataInsightsService.analyzeAndVisualize(request, participant);

        // Verify the response
        StepVerifier.create(reactor.core.publisher.Mono.fromFuture(analysisResult))
                .expectNextMatches(response -> {
                    boolean isValid = response != null &&
                                    response.getGeneratedComponent() != null &&
                                    response.getGeneratedComponent().getReactCode() != null &&
                                    !response.getGeneratedComponent().getReactCode().isEmpty();

                    if (isValid) {
                        log.info("Generated React Code Preview:");
                        String reactCode = response.getGeneratedComponent().getReactCode();
                        String preview = reactCode.length() > 200 ? 
                                       reactCode.substring(0, 200) + "..." : reactCode;
                        log.info(preview);
                        
                        // Check that the generated code contains expected patterns
                        boolean containsExpectedPatterns = 
                            reactCode.contains("React") || reactCode.contains("import") ||
                            reactCode.contains("chart") || reactCode.contains("Bar");
                        
                        if (!containsExpectedPatterns) {
                            log.warn("Generated code doesn't seem to contain expected React/chart patterns");
                        }
                    }

                    return isValid;
                })
                .verifyComplete();
    }

    @Test
    public void testSuggestVisualizations() {
        // Skip test if OpenAI API key is not configured
        Assumptions.assumeTrue(openAiApiKey != null && !"test-key-placeholder".equals(openAiApiKey), 
                             "OpenAI API key not configured - skipping AI insights test");
        // Create test structure and data  
        StructureAndPersonHolder holder = createAndVerify(3, true, 
            new DefaultEntityContext(new DummyParticipant("testTenant3", "testUser3")), 
            "_datainsightstest3");

        assertNotNull(holder);
        
        // Sync the index
        EntityContext context = new DefaultEntityContext(new DummyParticipant("testTenant3", "testUser3"));
        entitiesService.syncIndex(holder.getStructure().getId(), context).join();

        // Test the suggestVisualizations method
        DummyParticipant participant = new DummyParticipant("testTenant3", "testUser3");
        var suggestionsResult = dataInsightsService.suggestVisualizations(holder.getStructure().getId(), participant);

        // Verify the suggestions
        StepVerifier.create(reactor.core.publisher.Mono.fromFuture(suggestionsResult))
                .expectNextMatches(suggestions -> {
                    boolean isValid = suggestions != null && !suggestions.isEmpty();
                    
                    if (isValid) {
                        log.info("Received {} visualization suggestions:", suggestions.size());
                        suggestions.forEach(suggestion -> {
                            log.info("- {}: {} (confidence: {})", 
                                   suggestion.getVisualizationType(), 
                                   suggestion.getDescription(),
                                   suggestion.getConfidenceScore());
                        });
                    } else {
                        log.error("No visualization suggestions received");
                    }

                    return isValid;
                })
                .verifyComplete();
    }
}