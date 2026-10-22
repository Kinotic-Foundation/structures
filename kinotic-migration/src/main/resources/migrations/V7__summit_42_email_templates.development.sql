-- A development stack runs Summit 42 on its vite dev server, so Summit 42's emails link to http://localhost:5175.

UPDATE kinotic_system_email_template SET uiBaseUrl = 'http://localhost:5175'
    WHERE id = 'summit-42-invitation' OR id = 'summit-42-verification' WITH REFRESH;
