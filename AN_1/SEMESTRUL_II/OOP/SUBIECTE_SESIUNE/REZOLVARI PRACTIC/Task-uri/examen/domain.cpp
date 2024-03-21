#include "domain.h"
#include <cassert>

void test_creaza()
{
	Task t(1, "desc", "open", { "pr1","pr2" });
	assert(t.get_id() == 1);
	assert(t.get_descriere() == "desc");
	assert(t.get_stare() == "open");
	assert(t.get_programatori()[0] == "pr1");
	assert(t.get_programatori()[1] == "pr2");
	
}

void test_set() {
	Task t(1, "desc", "open", { "pr1","pr2" });
	t.set_stare("closed");
	assert(t.get_stare() == "closed");
}