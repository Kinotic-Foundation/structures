import { computed, ref, type ComputedRef, type Ref } from 'vue'
import { Kinotic, Pageable } from '@kinotic-ai/core'
import { type MachineParticipantIdentity, type Member, type Subject, SubjectKind } from '@kinotic-ai/management-api'

/** Who a platform grant can be made to, as the pickers and the grant rows show them. */
export interface SubjectOption {
  subject: Subject
  /** The operator's name or email, or the machine's name. */
  label: string
  /** The operator's email beneath the name, or the machine's client id. */
  detail: string | null
  /** What the subject is: an operator who signs in, or a machine that connects. */
  staff: 'Operator' | 'Machine'
}

// The pickers and the names on the grant rows read the first pages of the platform's operators and machines,
// which cover a platform of a few hundred of each; a subject beyond them is shown by its id
const SUBJECT_PAGE_SIZE = 500

/**
 * The platform's operators and machines as grant subjects, read once per page: the pickers list them and the
 * grant rows name them.
 */
export function useSubjects(): {
  operators: Ref<SubjectOption[]>
  machines: Ref<SubjectOption[]>
  load: () => Promise<void>
  optionOf: ComputedRef<(subject: Subject) => SubjectOption | undefined>
  labelOf: ComputedRef<(subject: Subject) => string>
} {
  const operators = ref<SubjectOption[]>([])
  const machines = ref<SubjectOption[]>([])

  async function load(): Promise<void> {
    const [userPage, machinePage] = await Promise.all([
      Kinotic.systemMembers.findUsers(Pageable.create(0, SUBJECT_PAGE_SIZE)),
      Kinotic.systemMembers.findMachines(Pageable.create(0, SUBJECT_PAGE_SIZE))
    ])
    // an operator added by email who has no account yet has no id to grant to until they first sign in
    operators.value = (userPage.content ?? []).filter(user => user.id).map(operatorOption)
    machines.value = (machinePage.content ?? []).map(machineOption)
  }

  const optionOf = computed(() => {
    const options = new Map<string, SubjectOption>()
    for (const option of [...operators.value, ...machines.value]) {
      options.set(option.subject.id, option)
    }
    return (subject: Subject) => options.get(subject.id)
  })

  const labelOf = computed(() => (subject: Subject) => optionOf.value(subject)?.label ?? subject.id)

  return { operators, machines, load, optionOf, labelOf }
}

function operatorOption(user: Member): SubjectOption {
  return {
    subject: { kind: SubjectKind.USER, id: user.id ?? '' },
    label: user.displayName || user.email,
    detail: user.displayName ? user.email : null,
    staff: 'Operator'
  }
}

function machineOption(machine: MachineParticipantIdentity): SubjectOption {
  return {
    subject: { kind: SubjectKind.USER, id: machine.id ?? '' },
    label: machine.displayName || machine.id || '',
    detail: machine.displayName ? machine.id ?? null : null,
    staff: 'Machine'
  }
}
