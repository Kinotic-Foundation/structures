-- The development server serves Summit 42 from https://dev-summit42.kinotic.ai, so Summit 42's emails link there.

UPDATE kinotic_system_email_template SET uiBaseUrl = 'https://dev-summit42.kinotic.ai'
    WHERE id = 'summit-42-invitation' OR id = 'summit-42-verification' WITH REFRESH;
