package eu.depau.kotlet.android.parcelable

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import eu.depau.kotlet.android.BuildConfig
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

/**
 * Kotlin Parcelable Boilerplate
 * Adapted from https://gist.github.com/cbeyls/72fbc1a24735beb792e2173b0199cbff#file-parcelableutils-kt
 *
 * ## How to use:
 *
 * 1. Implement the KotletParcelable interface (ignore the "incomplete Parcelable implementation" warning for now)
 *
 * 2. Add a constructor that reads the data from a parcel:
 * <pre>
 * constructor(parcel: Parcel) : this(
 *     device = parcel.readTypedObjectCompat(Device.CREATOR)!!,
 *     myFlag = parcel.readBoolean()
 * )
 * </pre>
 *
 * 3. Implement `.writeToParcel()`:
 * <pre>
 * override fun writeToParcel(parcel: Parcel, flags: Int) {
 *     parcel.writeTypedObjectCompat(usbDevice, flags)
 *     parcel.writeBoolean(myFlag)
 * }
 * </pre>
 *
 * 4. Add `CREATOR` to companion object:
 * <pre>
 * companion object {
 *     @JvmField
 *     val CREATOR = parcelableCreator(::MyParcelableClass)
 * }
 * </pre>
 *
 * 5. Done ;)
 *
 */
interface KotletParcelable : Parcelable {
    override fun describeContents() = 0
    override fun writeToParcel(parcel: Parcel, flags: Int)
}

// Creator factory functions

inline fun <reified T> parcelableCreator(
    crossinline create: (Parcel) -> T
) =
    object : Parcelable.Creator<T> {
        override fun createFromParcel(source: Parcel) = create(source)
        override fun newArray(size: Int) = arrayOfNulls<T>(size)
    }

inline fun <reified T> parcelableClassLoaderCreator(
    crossinline create: (Parcel, ClassLoader) -> T
) =
    object : Parcelable.ClassLoaderCreator<T> {
        override fun createFromParcel(source: Parcel, loader: ClassLoader) =
            create(source, loader)

        override fun createFromParcel(source: Parcel) =
            createFromParcel(source, T::class.java.classLoader!!)

        override fun newArray(size: Int) = arrayOfNulls<T>(size)
    }

// Parcel extensions

inline fun Parcel.readBoolean() = readInt() != 0

inline fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) 1 else 0)

inline fun <reified T : Enum<T>> Parcel.readEnum() =
    readInt().let { if (it >= 0) enumValues<T>()[it] else null }

inline fun <T : Enum<T>> Parcel.writeEnum(value: T?) =
    writeInt(value?.ordinal ?: -1)

inline fun <T> Parcel.readNullable(reader: () -> T) =
    if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: (T) -> Unit) {
    if (value != null) {
        writeInt(1)
        writer(value)
    } else {
        writeInt(0)
    }
}

fun Parcel.readDate() =
    readNullable { Date(readLong()) }

fun Parcel.writeDate(value: Date?) =
    writeNullable(value) { writeLong(it.time) }

fun Parcel.readBigInteger() =
    readNullable { BigInteger(createByteArray()) }

fun Parcel.writeBigInteger(value: BigInteger?) =
    writeNullable(value) { writeByteArray(it.toByteArray()) }

fun Parcel.readBigDecimal() =
    readNullable { BigDecimal(BigInteger(createByteArray()), readInt()) }

fun Parcel.writeBool(value: Boolean) = writeInt(if (value) 1 else 0)

fun Parcel.readBool(): Boolean = readInt() != 0

fun Parcel.writeBigDecimal(value: BigDecimal?) = writeNullable(value) {
    writeByteArray(it.unscaledValue().toByteArray())
    writeInt(it.scale())
}

fun <T : Parcelable> Parcel.readTypedObjectCompat(c: Parcelable.Creator<T>) =
    readNullable { c.createFromParcel(this) }

fun <T : Parcelable> Parcel.writeTypedObjectCompat(value: T?, parcelableFlags: Int) =
    writeNullable(value) { it.writeToParcel(this, parcelableFlags) }