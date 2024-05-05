package com.sixkids.teacher.main.organization;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ClassListViewModel_Factory implements Factory<OrganizationViewModel> {
  @Override
  public OrganizationViewModel get() {
    return newInstance();
  }

  public static ClassListViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OrganizationViewModel newInstance() {
    return new OrganizationViewModel();
  }

  private static final class InstanceHolder {
    private static final ClassListViewModel_Factory INSTANCE = new ClassListViewModel_Factory();
  }
}
