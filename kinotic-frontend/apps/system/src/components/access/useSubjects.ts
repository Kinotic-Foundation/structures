import { computed, ref, type ComputedRef, type Ref } from 'vue'
import { Kinotic, Pageable } from '@kinotic-ai/core'
import { type MachineParticipantIdentity, type Subject, SubjectKind, type SystemParticipantIdentity } from '@kinotic-ai/management-api'

/** Who a platform grant can be made to, as the pickers and the grant rows show them. */
export interface SubjectOption {
  subject: Subject
  /** The system user's name or email, or the machine's name. */
  label: string
  /** The system user's email beneath the name, or the machine's client id. */
  detail: string | null
  /** What the subject is: a system user who signs in, or a machine that connects. */
  staff: 'System user' | 'Machine'
}

// The pickers and the names on the grant rows read the first pages of the platform's system users and machines,
// which cover a platform of a few hundred of each; a subject beyond them is shown by its id
const SUBJECT_PAGE_SIZE = 500

/**
 * The platform's system users and machines as grant subjects, read once per page: the pickers list them and the
 * grant rows name them.
 */
export function useSubjects(): {
  systemUsers: Ref<SubjectOption[]>
  machines: Ref<SubjectOption[]>
  load: () => Promise<void>
  optionOf: ComputedRef<(subject: Subject) => SubjectOption | undefined>
  labelOf: ComputedRef<(subject: Subject) => string>
} {
  const systemUsers = ref<SubjectOption[]>([])
  const machines = ref<SubjectOption[]>([])

  async function load(): Promise<void> {
    const [userPage, machinePage] = await Promise.all([
      Kinotic.systemMembers.findUsers(Pageable.create(0, SUBJECT_PAGE_SIZE)),
      Kinotic.systemMembers.findMachines(Pageable.create(0, SUBJECT_PAGE_SIZE))
    ])
    systemUsers.value = (userPage.content ?? []).map(systemUserOption)
    machines.value = (machinePage.content ?? []).map(machineOption)
  }

  const optionOf = computed(() => {
    const options = new Map<string, SubjectOption>()
    for (const option of [...systemUsers.value, ...machines.value]) {
      options.set(option.subject.id, option)
    }
    return (subject: Subject) => options.get(subject.id)
  })

  const labelOf = computed(() => (subject: Subject) => optionOf.value(subject)?.label ?? subject.id)

  return { systemUsers, machines, load, optionOf, labelOf }
}

function systemUserOption(user: SystemParticipantIdentity): SubjectOption {
  return {
    subject: { kind: SubjectKind.USER, id: user.id ?? '' },
    label: user.displayName || user.email,
    detail: user.displayName ? user.email : null,
    staff: 'System user'
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
