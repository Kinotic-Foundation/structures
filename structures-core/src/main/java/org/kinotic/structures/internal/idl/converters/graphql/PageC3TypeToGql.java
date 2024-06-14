package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLNamedOutputType;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.structures.api.domain.idl.PageC3Type;
import org.kinotic.structures.internal.utils.GqlUtils;

import static graphql.schema.GraphQLNonNull.nonNull;

/**
 * Converts a {@link PageC3Type} to an OpenApi {@link Schema}
 * Created by Navíd Mitchell 🤪 on 5/7/24.
 */
public class PageC3TypeToGql implements C3TypeConverter<GqlTypeHolder, PageC3Type, GqlConversionState> {
    @Override
    public boolean supports(C3Type c3Type) {
        return c3Type instanceof PageC3Type;
    }

    @Override
    public GqlTypeHolder convert(PageC3Type c3Type, C3ConversionContext<GqlTypeHolder, GqlConversionState> conversionContext) {
        GqlTypeHolder ret = conversionContext.convert(c3Type.getContentType());
        ret.setInputType(null); // page types are always output only
        // This is safe to assume because pageC3Type.getContentType() is an ObjectC3Type
        ret.setOutputType(nonNull(GqlUtils.wrapTypeWithPage((GraphQLNamedOutputType)ret.getOutputType())));
        return ret;
    }

}
