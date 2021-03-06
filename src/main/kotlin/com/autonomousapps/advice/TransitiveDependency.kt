package com.autonomousapps.advice

/**
 * A dependency that has not been declared, but which is included by one or more other dependencies,
 * transitively.
 */
data class TransitiveDependency(
  /**
   * A tuple of an `identifier` and a resolved version. See [Dependency].
   */
  override val dependency: Dependency,
  /**
   * The "parent" dependencies that all contribute this transitive dependency.
   */
  val parents: Set<Dependency>
) : HasDependency, Comparable<TransitiveDependency> {

  override fun compareTo(other: TransitiveDependency): Int = dependency.compareTo(other.dependency)
}