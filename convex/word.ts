import { query } from "./_generated/server";

export const getWords = query({
  args: {},
  handler: async (ctx) => {
    return await ctx.db.query("word").collect();
  },
});