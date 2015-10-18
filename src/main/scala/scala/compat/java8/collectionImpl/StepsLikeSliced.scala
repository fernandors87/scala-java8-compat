package scala.compat.java8.collectionImpl

import java.util.Spliterator
import Stepper._

/** Abstracts all the generic operations of stepping over a collection that can be sliced into pieces.
  * `next` must update `i` but not `i0` so that later splitting steps can keep track of whether the
  * collection needs some sort of modification before transmission to the subclass.
  */
abstract class AbstractStepsLikeSliced[Coll, Sub >: Null, Semi <: Sub](protected var underlying: Coll, protected var i: Int, protected var iN: Int) {
  protected var i0: Int = i
  def semiclone(halfHint: Int): Semi  // Must really do all the work for both this and cloned collection!
  def characteristics(): Int = Ordered
  def estimateSize(): Long = iN - i
  def substep(): Sub = if (estimateSize > 0) semiclone((iN + i) >>> 1) else null
}

/** Abstracts the operation of stepping over a generic collection that can be efficiently sliced or otherwise subdivided */
abstract class StepsLikeSliced[A, AA, STA >: Null <: StepsLikeSliced[A, AA, _]](_underlying: AA, _i0: Int, _iN: Int)
  extends AbstractStepsLikeSliced[AA, AnyStepper[A], STA](_underlying, _i0, _iN)
  with AnyStepper[A]
{}

/** Abstracts the operation of stepping over a collection of Doubles that can be efficiently sliced or otherwise subdivided */
abstract class StepsDoubleLikeSliced[AA, STA >: Null <: StepsDoubleLikeSliced[AA, STA]](_underlying: AA, _i0: Int, _iN: Int)
  extends AbstractStepsLikeSliced[AA, DoubleStepper, STA](_underlying, _i0, _iN)
  with DoubleStepper
{}

/** Abstracts the operation of stepping over a collection of Ints that can be efficiently sliced or otherwise subdivided */
abstract class StepsIntLikeSliced[AA, STA >: Null <: StepsIntLikeSliced[AA, STA]](_underlying: AA, _i0: Int, _iN: Int)
  extends AbstractStepsLikeSliced[AA, IntStepper, STA](_underlying, _i0, _iN)
  with IntStepper
{}

/** Abstracts the operation of stepping over a collection of Longs that can be efficiently sliced or otherwise subdivided */
abstract class StepsLongLikeSliced[AA, STA >: Null <: StepsLongLikeSliced[AA, STA]](_underlying: AA, _i0: Int, _iN: Int)
  extends AbstractStepsLikeSliced[AA, LongStepper, STA](_underlying, _i0, _iN)
  with LongStepper
{}
