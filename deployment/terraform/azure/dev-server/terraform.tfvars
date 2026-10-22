# The shared development server — one per platform zone (see main.tf).
environment = "dev"
project     = "kinotic"
location    = "centralus"

# Summit 42's UI, whose sign-in and sign-up run through the management server
trusted_ui_hostnames = ["dev-summit42.kinotic.ai"]
