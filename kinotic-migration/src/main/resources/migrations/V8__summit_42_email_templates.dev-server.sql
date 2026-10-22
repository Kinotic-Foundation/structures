-- The development server also runs with the production profile, so V6 seeds Summit 42's templates there; their links
-- open https://dev-summit42.kinotic.ai, where the development server serves Summit 42 from.

UPDATE kinotic_system_email_template SET uiBaseUrl = 'https://dev-summit42.kinotic.ai'
    WHERE id = 'summit-42-invitation' OR id = 'summit-42-verification' WITH REFRESH;
