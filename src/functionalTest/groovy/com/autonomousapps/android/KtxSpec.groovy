package com.autonomousapps.android

import com.autonomousapps.fixtures.KtxProject
import com.autonomousapps.internal.android.AgpVersion
import spock.lang.Unroll

import static com.autonomousapps.utils.Runner.build
import static com.google.common.truth.Truth.assertThat

@SuppressWarnings("GroovyAssignabilityCheck")
final class KtxSpec extends AbstractAndroidSpec {

  @Unroll
  def "ktx dependencies are treated per user configuration (#gradleVersion, AGP #agpVersion, ignoreKtx=#ignoreKtx, useKtx=#useKtx)"() {
    given:
    def project = new KtxProject(agpVersion, ignoreKtx, useKtx)
    androidProject = project.newProject()

    when:
    build(gradleVersion, androidProject, 'buildHealth')

    then:
    def actualAdviceForApp = androidProject.adviceFor(project.appSpec)
    def expectedAdviceForApp = project.expectedAdviceForApp
    assertThat(expectedAdviceForApp).containsExactlyElementsIn(actualAdviceForApp)

    // This test is too expensive, so we're only going to test against the latest AGP
    where:
    [gradleVersion, agpVersion, useKtx, ignoreKtx] << gradleAgpMatrixPlus(AgpVersion.version('4.1'), [true, false], [true, false])
  }
}
