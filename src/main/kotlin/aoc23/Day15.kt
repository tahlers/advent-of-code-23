package aoc23

object Day15 {

    data class Lens(val label: String, val focalLength: Int)

    sealed class Operator(open val name: String) {
        val box: Int
            get() = hash(name)

        data class Dash(override val name: String) : Operator(name)
        data class Equal(override val name: String, val lens: Lens) : Operator(name)
    }

    fun calculateHashSum(input: String): Int {
        val steps = input.trim().split(',')
        val hashes = steps.map { hash(it) }
        return hashes.sum()
    }

    fun calculateFocusingPower(input: String): Int {
        val ops = parseInput(input)
        val boxes: Map<Int, List<Lens>> = emptyMap()
        val placement = ops.fold(boxes) { acc, op ->
            when (op) {
                is Operator.Dash -> {
                    val newBox = acc[op.box]?.filter { it.label != op.name }
                    if (newBox == null) acc else acc + (op.box to newBox)
                }

                is Operator.Equal -> {
                    val box = acc.getOrDefault(op.box, emptyList())
                    val newBox = if (op.lens.label in box.map { it.label }) {
                        box.map { if (it.label == op.lens.label) op.lens else it }
                    } else {
                        box + op.lens
                    }
                    acc + (op.box to newBox)
                }
            }
        }
        val focusingPowers = placement.map { (box, lenses) ->
            lenses.mapIndexed { idx, lens ->
                val boxFactor = box + 1
                val slotFactor = idx + 1
                boxFactor * slotFactor * lens.focalLength
            }.sum()
        }
        return focusingPowers.sum()
    }

    private fun hash(input: String): Int = input
        .fold(0) { acc, c ->
            val ascii = c.code
            val newValue = (acc + ascii) * 17
            newValue % 256
        }

    private fun parseInput(input: String): List<Operator> {
        val steps = input.trim().split(',')
        return steps.map { step ->
            if ('-' in step) {
                Operator.Dash(step.substringBefore('-'))
            } else {
                val label = step.substringBefore('=')
                val focalLength = step.substringAfter('=').toInt()
                Operator.Equal(label, Lens(label, focalLength))
            }
        }
    }
}
