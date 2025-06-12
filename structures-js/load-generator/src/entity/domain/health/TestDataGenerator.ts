import { faker } from '@faker-js/faker'
import { Patient } from './Patient'
import { Provider } from './Provider'
import { Diagnosis } from './Diagnosis'
import { Treatment } from './Treatment'
import { Appointment } from './Appointment'
import { Address } from './Address'
import { ContactInfo } from './ContactInfo'
import { InsuranceInfo } from './InsuranceInfo'
import { Qualification } from './Qualification'
import { Specialty } from './Specialty'
import { AppointmentStatus } from './AppointmentStatus'
import { AppointmentType } from './AppointmentType'
import { DiagnosisStatus } from './DiagnosisStatus'
import { TreatmentStatus } from './TreatmentStatus'

export class TestDataGenerator {
    private static readonly SPECIALTIES = [
        'Cardiology', 'Dermatology', 'Endocrinology', 'Gastroenterology',
        'Neurology', 'Oncology', 'Orthopedics', 'Pediatrics',
        'Psychiatry', 'Rheumatology', 'Urology', 'Ophthalmology'
    ]

    private static readonly CONDITIONS = [
        'Hypertension', 'Type 2 Diabetes', 'Asthma', 'Arthritis',
        'Migraine', 'Anxiety', 'Depression', 'Obesity',
        'Heart Disease', 'Chronic Pain', 'Fibromyalgia', 'Sleep Apnea'
    ]

    private static readonly TREATMENTS = [
        'Medication', 'Physical Therapy', 'Surgery', 'Counseling',
        'Lifestyle Changes', 'Dietary Changes', 'Exercise Program',
        'Alternative Therapy', 'Rehabilitation', 'Preventive Care'
    ]

    static generatePatient(): Patient {
        const patient = Patient.builder()
            .withFirstName(faker.person.firstName())
            .withLastName(faker.person.lastName())
            .withDateOfBirth(faker.date.birthdate({ min: 0, max: 100, mode: 'age' }))
            .withGender(faker.helpers.arrayElement(['Male', 'Female', 'Other']))
            .withAddresses([this.generateAddress()])
            .withContactInfo(this.generateContactInfo())
            .withInsuranceInfo(this.generateInsuranceInfo())
            .withMedicalHistory(faker.helpers.arrayElements(this.CONDITIONS, { min: 0, max: 3 }))
            .withAllergies(faker.helpers.arrayElements(['Penicillin', 'Peanuts', 'Shellfish', 'Latex', 'Pollen'], { min: 0, max: 3 }))
            .withBloodType(faker.helpers.arrayElement(['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-']))
            .withEmergencyContact(this.generateContactInfo())
            .withCreatedAt(faker.date.past())
            .withUpdatedAt(faker.date.recent())
            .build()

        return patient
    }

    static generateProvider(): Provider {
        const provider = Provider.builder()
            .withFirstName(faker.person.firstName())
            .withLastName(faker.person.lastName())
            .withTitle(faker.helpers.arrayElement(['Dr.', 'Prof.', 'MD', 'DO']))
            .withSpecialties([this.generateSpecialty()])
            .withQualifications([this.generateQualification()])
            .withAddresses([this.generateAddress()])
            .withContactInfo(this.generateContactInfo())
            .withLicenseNumber(faker.string.alphanumeric(8).toUpperCase())
            .withLicenseExpiryDate(faker.date.future())
            .withIsActive(faker.datatype.boolean())
            .withCreatedAt(faker.date.past())
            .withUpdatedAt(faker.date.recent())
            .build()

        return provider
    }

    static generateDiagnosis(patientId: string, providerId: string): Diagnosis {
        const diagnosis = Diagnosis.builder()
            .withPatientId(patientId)
            .withProviderId(providerId)
            .withCondition(faker.helpers.arrayElement(this.CONDITIONS))
            .withDescription(faker.lorem.paragraph())
            .withStatus(faker.helpers.arrayElement(Object.values(DiagnosisStatus)))
            .withDiagnosisDate(faker.date.recent())
            .withSeverity(faker.helpers.arrayElement(['Mild', 'Moderate', 'Severe']))
            .withNotes(faker.lorem.paragraphs(2))
            .withCreatedAt(faker.date.past())
            .withUpdatedAt(faker.date.recent())
            .build()

        return diagnosis
    }

    static generateTreatment(patientId: string, providerId: string, diagnosisId: string): Treatment {
        const treatment = Treatment.builder()
            .withPatientId(patientId)
            .withProviderId(providerId)
            .withDiagnosisId(diagnosisId)
            .withName(faker.helpers.arrayElement(this.TREATMENTS))
            .withDescription(faker.lorem.paragraph())
            .withStatus(faker.helpers.arrayElement(Object.values(TreatmentStatus)))
            .withStartDate(faker.date.recent())
            .withEndDate(faker.date.future())
            .withFrequency(faker.helpers.arrayElement(['Daily', 'Weekly', 'Monthly', 'As needed']))
            .withDosage(faker.helpers.arrayElement(['10mg', '20mg', '50mg', '100mg', 'As prescribed']))
            .withNotes(faker.lorem.paragraphs(2))
            .withCreatedAt(faker.date.past())
            .withUpdatedAt(faker.date.recent())
            .build()

        return treatment
    }

    static generateAppointment(patientId: string, providerId: string): Appointment {
        const startTime = faker.date.future()
        const duration = faker.helpers.arrayElement([15, 30, 45, 60])
        const endTime = new Date(startTime.getTime() + duration * 60000)

        const appointment = Appointment.builder()
            .withPatientId(patientId)
            .withProviderId(providerId)
            .withType(faker.helpers.arrayElement(Object.values(AppointmentType)))
            .withStatus(faker.helpers.arrayElement(Object.values(AppointmentStatus)))
            .withStartTime(startTime)
            .withEndTime(endTime)
            .withDuration(duration)
            .withNotes(faker.lorem.paragraph())
            .withIsRecurring(faker.datatype.boolean())
            .withRecurrencePattern(faker.helpers.arrayElement(['Daily', 'Weekly', 'Monthly', 'None']))
            .withCreatedAt(faker.date.past())
            .withUpdatedAt(faker.date.recent())
            .build()

        return appointment
    }

    private static generateAddress(): Address {
        return Address.builder()
            .withStreet(faker.location.streetAddress())
            .withCity(faker.location.city())
            .withState(faker.location.state())
            .withPostalCode(faker.location.zipCode())
            .withCountry(faker.location.country())
            .build()
    }

    private static generateContactInfo(): ContactInfo {
        return ContactInfo.builder()
            .withEmail(faker.internet.email())
            .withPhoneNumber(faker.phone.number())
            .withEmergencyContactName(faker.person.fullName())
            .build()
    }

    private static generateInsuranceInfo(): InsuranceInfo {
        return InsuranceInfo.builder()
            .withProviderName(faker.helpers.arrayElement(['Blue Cross', 'Aetna', 'UnitedHealthcare', 'Cigna', 'Kaiser Permanente']))
            .withPolicyNumber(faker.string.alphanumeric(10).toUpperCase())
            .withGroupNumber(faker.string.alphanumeric(8).toUpperCase())
            .withCoverageStartDate(faker.date.past())
            .withCoverageEndDate(faker.date.future())
            .withIsPrimary(faker.datatype.boolean())
            .withCopayAmount(faker.number.float({ min: 10, max: 50, fractionDigits: 2 }))
            .withDeductibleAmount(faker.number.float({ min: 500, max: 5000, fractionDigits: 2 }))
            .withOutOfPocketMaximum(faker.number.float({ min: 1000, max: 10000, fractionDigits: 2 }))
            .withIsActive(faker.datatype.boolean())
            .build()
    }

    private static generateQualification(): Qualification {
        return Qualification.builder()
            .withDegree(faker.helpers.arrayElement(['MD', 'DO', 'PhD', 'MPH', 'MSN']))
            .withInstitution(faker.company.name())
            .withCertification(faker.helpers.arrayElement(['Board Certified', 'Fellowship', 'Specialty Certification']))
            .withYearObtained(faker.number.int({ min: 1990, max: 2024 }))
            .withIsVerified(faker.datatype.boolean())
            .withVerificationDate(faker.date.past())
            .withExpiryDate(faker.date.future())
            .withIsActive(true)
            .build()
    }

    private static generateSpecialty(): Specialty {
        return Specialty.builder()
            .withName(faker.helpers.arrayElement(this.SPECIALTIES))
            .withDescription(faker.lorem.sentence())
            .withCertificationRequired(faker.datatype.boolean())
            .withYearsOfTraining(faker.number.int({ min: 3, max: 8 }))
            .withIsActive(true)
            .build()
    }

    static generateTestData(count: number = 20): {
        patients: Patient[],
        providers: Provider[],
        diagnoses: Diagnosis[],
        treatments: Treatment[],
        appointments: Appointment[]
    } {
        const patients = Array.from({ length: count }, () => this.generatePatient())
        const providers = Array.from({ length: count }, () => this.generateProvider())
        
        // Generate diagnoses for each patient from random providers
        const diagnoses = patients.flatMap(patient => 
            Array.from({ length: faker.number.int({ min: 1, max: 3 }) }, () => 
                this.generateDiagnosis(
                    patient.id!,
                    faker.helpers.arrayElement(providers).id!
                )
            )
        )

        // Generate treatments for each diagnosis
        const treatments = diagnoses.flatMap(diagnosis => 
            Array.from({ length: faker.number.int({ min: 1, max: 2 }) }, () => 
                this.generateTreatment(
                    diagnosis.patientId,
                    diagnosis.providerId,
                    diagnosis.id!
                )
            )
        )

        // Generate appointments for each patient with random providers
        const appointments = patients.flatMap(patient => 
            Array.from({ length: faker.number.int({ min: 1, max: 5 }) }, () => 
                this.generateAppointment(
                    patient.id!,
                    faker.helpers.arrayElement(providers).id!
                )
            )
        )

        return {
            patients,
            providers,
            diagnoses,
            treatments,
            appointments
        }
    }
} 